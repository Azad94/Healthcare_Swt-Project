/*******************************************************************************
 * Copyright 2017  Chris Deter, Arne Salveter, Sven Marquardt
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package space.objectfinder.backend.service.reminder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.Task;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.CleaningTaskRepository;
import space.objectfinder.backend.service.MaintainanceTaskRepository;
import space.objectfinder.backend.service.RoleRepository;
import space.objectfinder.backend.service.TransportTaskRepository;
import space.objectfinder.backend.service.UserRepository;

/**
 * Diese Klasse verwaltet die Services die von der Datenbank regelmäßig
 * ausgeführt werden.
 *
 * Sie enthält die drei Services: Auftrag eskalieren, Auftrag periodisch neu
 * erstellen, Auftrag freigeben
 *
 * Dies deckt die Anwendungsfälle Auftrag eskalieren, Auftrag Freigeben ab
 *
 * @author Chris Deter
 * @since 21.05.2017
 */
@Component
public class DBTimerTask {

	private static final Logger log = LoggerFactory.getLogger(DBTimerTask.class);
	private static final String end = "\r\n";

	@Value("${DBTimerTask.daysUntilJobEscalates}")
	private static final int daysUntilJobEscalates = 1;
	@Value("${DBTimerTask.hoursUntilJobIsFreed}")
	private static final int hoursUntilJobIsFreed = 1;
	@Value("${DBTimerTask.daysUntilJobIsRecreated}")
	private static final int daysUntilJobIsRecreated = 1;
	@Value("${DBTimerTask.repeatState}")
	private static final int repeatState = 5;
	@Value("${DBTimerTask.notifyGroup}")
	String roleName = "";

	Timer timer;

	@Autowired
	private EmailClient mailClient;

	@Autowired
	private TransportTaskRepository transpoTask;

	@Autowired
	private CleaningTaskRepository cleanTask;

	@Autowired
	private MaintainanceTaskRepository maintainRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;

	/**
	 * Diese Methode wird (fixedRate = 21600000) 4x am Tag ausgelöst. Sie prüft
	 * ob Task eskaliert werden müssen. Wenn ein Task eskaliert werden muss,
	 * weil er nicht innerhalb der im Server konfigurierten Zeit bearbeitet
	 * wurde, dann wird eine E-Mail an die hinterlegte Gruppe gesendet.
	 */
	@Scheduled(fixedRate = 21600000)
	public void escalateTask() {
		DBTimerTask.log.info("Find task to escalate {}", LocalDateTime.now());
		escalateTask(transpoTask.findAll());
		escalateTask(cleanTask.findAll());
		escalateTask(maintainRepo.findAll());
	}

	/**
	 * Löst das Senden einer Mail aus für einen Satz an Tasks
	 *
	 * @param it
	 *            ein Satz von Tasks
	 */
	private <T extends Task> void escalateTask(final Iterable<T> it) {
		for (final Task t : it) {
			final LocalDateTime escalateTime = t.getCreationTime().plusDays(DBTimerTask.daysUntilJobEscalates);
			if (t.getAcceptedTime() != null && escalateTime.isBefore(LocalDateTime.now())
					&& t.getClosedTime() == null) {
				sendEmail("Task escalated", t.getDescription(), t.getAcceptedTime().toString());
			}
		}
	}

	/**
	 * Diese Methode wird (fixedRate = 21600000) 4x am Tag ausgelöst. Sie prüft
	 * ob einige Task in der Zeit bearbeitet wurden, die in der Server Config
	 * hinterlegt wurde. Wenn diese Zeit in einem Task überschritten wurde, wird
	 * dieser Task wieder freigegeben.
	 */
	@Scheduled(fixedRate = 21600000)
	public void freeTask() {
		DBTimerTask.log.info("Find task, that need cleanup {}", new Date());
		for (final Task t : transpoTask.findAll()) {
			transpoTask.save((TransportTask) freeTask(t));
		}
		for (final Task t : cleanTask.findAll()) {
			cleanTask.save((CleaningTask) freeTask(t));
		}
		for (final Task t : maintainRepo.findAll()) {
			maintainRepo.save((MaintainanceTask) freeTask(t));
		}
	}

	/**
	 * Prüft ob der übergebene Task zu lange belegt war und gibt ihn
	 * gegebenenfalls frei
	 *
	 * @param t
	 *            der task
	 * @return den Task
	 */
	private Task freeTask(final Task t) {
		if (t.getAcceptedTime() != null
				&& t.getAcceptedTime().plusHours(DBTimerTask.hoursUntilJobIsFreed).isBefore(LocalDateTime.now())
				&& t.getEditor() != null) {
			DBTimerTask.log.info("Found MTask that is accepted since an hour");
			t.setEditor(null);
			t.setAcceptedTime(null);
		}
		return t;
	}

	/**
	 * Diese Methode wird (fixedRate = 21600000) 4x am Tag ausgelöst. Sie prüft
	 * ob ein Maintainance Task neu erstellt werden muss. Dafür prüft sie ob in
	 * der Wiederholungseigenschaft des Task nicht "0" steht und die Zeit zum
	 * neu erstellen des Tasks gekommen ist.
	 */
	@Scheduled(fixedRate = 21600000)
	public void recreateTask() {
		final LocalDateTime now = LocalDateTime.now();
		DBTimerTask.log.info("Search for Maintainance Tast that could be restarted {}", now);
		for (final MaintainanceTask mt : maintainRepo.findAll()) {
			final LocalDateTime repeatTime = mt.getCreationTime().plusDays(mt.getRepeatTaskInDays());
			if (mt.getRepeatTaskInDays() > 0 && repeatTime.isBefore(now)) {
				DBTimerTask.log.info("Found Maintainance Task that could be restarted {}", repeatTime);
				final MaintainanceTask mtnew = new MaintainanceTask();
				mtnew.setBeaconObject(mt.getBeaconObject());
				mtnew.setCreationTime(now);
				mtnew.setRepeatTaskInDays(mt.getRepeatTaskInDays());
				mtnew.setCreator(mt.getCreator());
				mtnew.setDescription(mt.getDescription());
				mtnew.setLevel(mt.getLevel());
				mtnew.setPicture(mt.getPicture());
				mtnew.setRole(mt.getRole());
				mt.setRepeatTaskInDays(0);
				maintainRepo.save(mtnew);
				maintainRepo.save(mt);
			}
		}
	}

	/**
	 * Sendet eine Mail
	 * 
	 * @param tasktype
	 *            Tasktyp
	 * @param taskDesc
	 *            Task Beschreibung
	 * @param taskTime
	 *            Task Zeit
	 */
	private void sendEmail(final String tasktype, final String taskDesc, final String taskTime) {
		final Role r = roleRepo.findByName(roleName);
		final String message = tasktype + " escalated quickly";
		DBTimerTask.log.info("Found {} that is createt since an hour", tasktype);
		for (final User u : userRepo.findByRole(r)) {
			mailClient.sendSimpleMessage(u.getEmail(), message,
					tasktype + DBTimerTask.end + taskDesc + DBTimerTask.end + taskTime);
		}
	}
}
