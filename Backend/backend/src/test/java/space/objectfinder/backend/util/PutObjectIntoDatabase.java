/*******************************************************************************
 * Copyright 2017 Chris Deter, Arne Salveter, Sven Marquardt
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
package space.objectfinder.backend.util;

import java.time.LocalDateTime;

import space.objectfinder.backend.domain.Beacon;
import space.objectfinder.backend.domain.BeaconObject;
import space.objectfinder.backend.domain.CleaningTask;
import space.objectfinder.backend.domain.Location;
import space.objectfinder.backend.domain.MaintainanceTask;
import space.objectfinder.backend.domain.Role;
import space.objectfinder.backend.domain.TransportTask;
import space.objectfinder.backend.domain.User;
import space.objectfinder.backend.service.BeaconObjectRepository;
import space.objectfinder.backend.service.BeaconRepository;
import space.objectfinder.backend.service.CleaningTaskRepository;
import space.objectfinder.backend.service.LocationRepository;
import space.objectfinder.backend.service.MaintainanceTaskRepository;
import space.objectfinder.backend.service.RoleRepository;
import space.objectfinder.backend.service.TransportTaskRepository;
import space.objectfinder.backend.service.UserRepository;

/**
 * Diese Klasse wird von dem Repository Test genutzt um Testdaten in die
 * Software einzufügen. Sie erstellt und entfernt Testdaten in der Datenbank.
 *
 * @author Chris Deter
 * @since 20.06.2017
 */
public class PutObjectIntoDatabase {
	LocalDateTime testtime;
	public final Role r = new Role();
	public final User u = new User();
	public final Location loc = new Location();
	public final Beacon beacon = new Beacon();
	public final BeaconObject beaconObj = new BeaconObject();

	public final MaintainanceTask mt = new MaintainanceTask();
	public final TransportTask tt = new TransportTask();
	public final CleaningTask ct = new CleaningTask();

	RoleRepository roleRepo;

	UserRepository userRepo;

	LocationRepository locRepo;

	BeaconRepository beaconRepo;

	BeaconObjectRepository beaconObjRepo;

	MaintainanceTaskRepository maintainanceRepo;

	TransportTaskRepository transpoRepo;

	CleaningTaskRepository cleaningRepo;

	public String roleName = "Administrator";
	public String userName = "Hans";
	String email = "test@test.de";
	Integer major = 1000;
	Integer minor = 1000;

	public PutObjectIntoDatabase(final RoleRepository roleR, final UserRepository userR, final LocationRepository locR,
			final BeaconRepository beaconR, final BeaconObjectRepository beaconObjR,
			final MaintainanceTaskRepository maintainanceR, final TransportTaskRepository transpoR,
			final CleaningTaskRepository cleaningR) {
		roleRepo = roleR;
		userRepo = userR;
		locRepo = locR;
		beaconRepo = beaconR;
		beaconObjRepo = beaconObjR;
		maintainanceRepo = maintainanceR;
		transpoRepo = transpoR;
		cleaningRepo = cleaningR;
	}

	public void clear() {
		cleaningRepo.delete(ct);
		transpoRepo.delete(tt);
		maintainanceRepo.delete(mt);
		beaconObjRepo.delete(beaconObj);
		beaconRepo.delete(beacon);
		locRepo.delete(loc);
		userRepo.delete(u);
		roleRepo.delete(r);
	}

	public BeaconObject getBeaconObj() {
		return beaconObj;
	}

	public Location getLoc() {
		return loc;
	}

	public LocalDateTime getNow() {
		return LocalDateTime.now();
	}

	public Role getRole() {
		return r;
	}

	public LocalDateTime getTime() {
		return testtime;
	}

	public User getUser() {
		return u;
	}

	public void setNewData(final String rolename, final String username, final String mail, final Integer maj,
			final Integer min, final String uid) {
		roleName = rolename;
		userName = username;
		email = mail;
		major = maj;
		minor = min;
	}

	public void setup() {
		testtime = getNow();
		// Create Role
		r.setName(roleName);
		roleRepo.save(r);

		// Create User
		u.setName(userName);
		u.setPassword(userName);
		u.setEmail(email);
		u.setRole(r);
		u.setLastUpdate(getTime());
		userRepo.save(u);

		// Create Location
		loc.setBuilding(1);
		loc.setFloor(2);
		loc.setRoom(3);
		locRepo.save(loc);

		// Create Beacon
		beacon.setUuid("0");
		beacon.setMajor(major);
		beacon.setMinor(minor);
		beaconRepo.save(beacon);

		// Create BeaconObj
		beaconObj.setBeacon(beacon);
		beaconObj.setLocation(loc);
		beaconObj.setName(userName);
		beaconObjRepo.save(beaconObj);

		// Create Tasks
		mt.setCreator(getUser());
		mt.setCreationTime(getTime());
		mt.setBeaconObject(getBeaconObj());

		maintainanceRepo.save(mt);

		tt.setCreator(getUser());
		tt.setCreationTime(getTime());
		tt.setTargetLocation(getLoc());
		tt.setBeaconObject(getBeaconObj());
		tt.setLastEdited(getTime());
		transpoRepo.save(tt);

		ct.setCreator(getUser());
		ct.setCreationTime(getTime());
		ct.setBeaconObject(getBeaconObj());
		ct.setLastEdited(getTime());
		cleaningRepo.save(ct);
	}
}
