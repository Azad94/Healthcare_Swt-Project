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
package space.objectfinder.backend.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
import space.objectfinder.backend.util.PutObjectIntoDatabase;

/**
 * Dieser Test fügt in jedes Repository ein Objekt ein und liest es anschließend
 * wieder aus
 *
 * @author Chris Deter
 * @since 01.05.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
	PutObjectIntoDatabase Objects;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	LocationRepository locRepo;

	@Autowired
	BeaconRepository beaconRepo;

	@Autowired
	BeaconObjectRepository beaconObjRepo;

	@Autowired
	MaintainanceTaskRepository maintainanceRepo;

	@Autowired
	TransportTaskRepository transpoRepo;

	@Autowired
	CleaningTaskRepository cleaningRepo;

	private final String username = "Peter";

	@Before
	public void setup() {
		Objects = new PutObjectIntoDatabase(roleRepo, userRepo, locRepo, beaconRepo, beaconObjRepo, maintainanceRepo,
				transpoRepo, cleaningRepo);
		Objects.setNewData("Nutzer", username, "quhberta@gmail.com", 1, 2, "3");
		Objects.setup();
	}

	@After
	public void clear() {
		Objects.clear();
	}

	@Test
	public void testRoleRepo() {
		final Role retRole = roleRepo.findOne(Objects.r.getId());
		Assert.assertTrue(retRole.getName().equals(Objects.roleName));
	}

	@Test
	public void testUserRepo() {
		final User retUser = userRepo.findOne(Objects.u.getId());
		Assert.assertTrue(retUser.getName().equals(username));
	}

	@Test
	public void testLocRepo() {
		final Location retLoc = locRepo.findOne(Objects.loc.getId());
		Assert.assertTrue(retLoc.getId() > 0);
	}

	@Test
	public void testBeaconRepo() {
		final Beacon b = beaconRepo.findOne(Objects.beacon.getUuid());
		Assert.assertTrue(b.getMajor() == Objects.beacon.getMajor());
	}

	@Test
	public void testBeaconObjRepo() {
		final BeaconObject retLoc = beaconObjRepo.findOne(Objects.beaconObj.getId());
		Assert.assertTrue(retLoc.getId() > 0);
	}

	@Test
	public void testMaintanance() {
		final MaintainanceTask mt = maintainanceRepo.findOne(Objects.mt.getId());
		Assert.assertTrue(mt != null);
		Assert.assertTrue(mt.getCreator().getName().equals(Objects.userName));
	}

	@Test
	public void testTransport() {
		final TransportTask mt = transpoRepo.findOne(Objects.tt.getId());
		Assert.assertTrue(mt != null);
		Assert.assertTrue(mt.getCreator().getName().equals(Objects.userName));
	}

	@Test
	public void testCleaning() {
		final CleaningTask mt = cleaningRepo.findOne(Objects.ct.getId());
		Assert.assertTrue(mt != null);
		Assert.assertTrue(mt.getCreator().getName().equals(Objects.userName));
	}
}
