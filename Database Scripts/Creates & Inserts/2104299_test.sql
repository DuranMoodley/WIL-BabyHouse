-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: fdb13.awardspace.net
-- Generation Time: Oct 31, 2016 at 08:53 PM
-- Server version: 5.7.13-log
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2104299_test`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`2104299_test`@`%` PROCEDURE `addBaby` (IN `babyGender` VARCHAR(30), IN `babyRace` VARCHAR(30), IN `dateArrived` DATE, IN `dateExited` DATE, IN `babyStatus` VARCHAR(35), IN `personId` INT(11))  NO SQL
INSERT INTO tbl_baby (tbl_baby.babyGender,tbl_baby.babyRace,tbl_baby.dateArrived,tbl_baby.dateExited,tbl_baby.babyStatus,tbl_baby.personID) VALUES (babyGender, babyRace,dateArrived,dateExited,babyStatus,personId)$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `addPerson` (IN `pFname` VARCHAR(30), IN `pLname` VARCHAR(30), IN `pEmail` VARCHAR(30), IN `pContactNumber` VARCHAR(11), IN `pPassword` VARCHAR(30))  NO SQL
INSERT INTO tbl_people(tbl_people.personFName,tbl_people.personLName,tbl_people.personEmail,tbl_people.personContact,tbl_people.personPassword) VALUES(pFname,pLname,pEmail,pContactNumber,pPassword)$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `addVolunteer` (IN `imageID` BLOB, IN `personID` INT(11))  NO SQL
INSERT INTO tbl_volunteer (tbl_volunteer.imageID,tbl_volunteer.PersonID) VALUES (imageID,personID)$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `addVolunterCheckInTimes` (IN `checkin` TIME, IN `checkout` TIME, IN `volId` INT(11), IN `date` VARCHAR(10), IN `timeWorked` INT(10))  NO SQL
INSERT INTO tbl_volunteer_attendance(tbl_volunteer_attendance.checkInTime,tbl_volunteer_attendance.checkOutTime,tbl_volunteer_attendance.volunteerID,tbl_volunteer_attendance.checkInDate,tbl_volunteer_attendance.amountOfTimeWorked)
VALUES(checkin,checkout,volId,date,timeWorked)$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `getEvents` ()  NO SQL
Select * From tbl_event$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `getVolunteerId` (IN `personId` VARCHAR(10))  NO SQL
SELECT tbl_volunteer.volunteerID FROM tbl_volunteer
WHERE tbl_volunteer.PersonID = personId$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `loginUser` (IN `password` VARCHAR(30), IN `emailAddress` VARCHAR(30))  NO SQL
SELECT * FROM tbl_people WHERE personPassword = password AND personEmail = emailAddress$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `updateBaby` (IN `gender` VARCHAR(10), IN `race` VARCHAR(30), IN `dateArrived` DATE, IN `dateExited` DATE, IN `status` VARCHAR(30), IN `personId` INT(11))  NO SQL
UPDATE tbl_baby SET tbl_baby.babyGender = gender , tbl_baby.babyRace = race, tbl_baby.dateArrived = dateArrived , tbl_baby.dateExited = dateExited , tbl_baby.babyStatus = status WHERE tbl_baby.personID = personId$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `updateVolunteer` (IN `imageId` BLOB, IN `personId` INT(11))  NO SQL
UPDATE tbl_volunteer SET tbl_volunteer.imageID = imageId WHERE tbl_volunteer.PersonID = personId$$

CREATE DEFINER=`2104299_test`@`%` PROCEDURE `wishListData` ()  NO SQL
SELECT * FROM tbl_wishlist$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_adopters`
--

CREATE TABLE `tbl_adopters` (
  `adopterID` int(11) NOT NULL,
  `babyID` int(11) DEFAULT NULL,
  `dateAdopted` date NOT NULL,
  `dateRegistered` date NOT NULL,
  `archivedParent` varchar(30) NOT NULL,
  `personID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_adopters`
--

INSERT INTO `tbl_adopters` (`adopterID`, `babyID`, `dateAdopted`, `dateRegistered`, `archivedParent`, `personID`) VALUES
(8, NULL, '2016-09-29', '2016-10-20', 'Andrea', 63);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_baby`
--

CREATE TABLE `tbl_baby` (
  `babyID` int(11) NOT NULL,
  `babyGender` varchar(10) NOT NULL,
  `babyRace` varchar(10) NOT NULL,
  `dateArrived` date NOT NULL,
  `dateExited` date NOT NULL,
  `babyStatus` varchar(30) NOT NULL,
  `personID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_baby`
--

INSERT INTO `tbl_baby` (`babyID`, `babyGender`, `babyRace`, `dateArrived`, `dateExited`, `babyStatus`, `personID`) VALUES
(9, 'Female', 'Black', '2016-10-27', '2016-10-27', 'NewArrival', 61),
(10, 'Male', 'White', '2016-10-27', '2016-10-29', 'NewArrival', 71),
(11, 'Male', 'Indian', '2016-10-27', '2016-10-29', 'Fostered', 73);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_blog`
--

CREATE TABLE `tbl_blog` (
  `id` int(11) NOT NULL,
  `title` varchar(225) NOT NULL,
  `content` longtext NOT NULL,
  `date` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_blog`
--

INSERT INTO `tbl_blog` (`id`, `title`, `content`, `date`) VALUES
(15, 'Test 2', 'Hi, This is a test of our \r\n\r\nKind Regards\r\nTest Team', 'Monday 31st of October 2016 11:12:57 AM');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_donors`
--

CREATE TABLE `tbl_donors` (
  `donorID` int(11) NOT NULL,
  `donorAmount` int(11) NOT NULL,
  `personID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_donors`
--

INSERT INTO `tbl_donors` (`donorID`, `donorAmount`, `personID`) VALUES
(24, 2000, 64);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_event`
--

CREATE TABLE `tbl_event` (
  `eventID` int(11) NOT NULL,
  `eventDate` date NOT NULL,
  `eventTitle` varchar(225) NOT NULL,
  `eventDescription` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_event`
--

INSERT INTO `tbl_event` (`eventID`, `eventDate`, `eventTitle`, `eventDescription`) VALUES
(3, '2016-10-28', 'Baby Birthday', 'Having a big party of all our babies'),
(4, '2016-10-29', 'Staff Party', 'Spoil our staff member with fun'),
(9, '2015-10-25', 'Test', 'Test Event'),
(10, '2015-10-25', 'Test', 'Test Event 2');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_people`
--

CREATE TABLE `tbl_people` (
  `personID` int(11) NOT NULL,
  `personFName` varchar(30) NOT NULL,
  `personLName` varchar(30) NOT NULL,
  `personAddress` varchar(100) NOT NULL,
  `personContact` int(10) NOT NULL,
  `personEmail` varchar(30) NOT NULL,
  `personPassword` varchar(30) NOT NULL,
  `isVolunteer` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_people`
--

INSERT INTO `tbl_people` (`personID`, `personFName`, `personLName`, `personAddress`, `personContact`, `personEmail`, `personPassword`, `isVolunteer`) VALUES
(61, 'Sachin', 'Moodley', 'mt Edgecombe ', 373637373, 's@gmail.com', '123', 'No'),
(62, 'Duran', 'Moodley', 'Town ', 2147483647, 'dm@gmail.com', '1234', 'Yes'),
(63, 'Warren', 'Edy', '12 Radar Drive', 846465456, 'warrenedy12@gmail.com', '565454', 'No'),
(64, 'Calvin', 'Reed', '12 Radar Drive', 620072925, 'cal@gmail.com', '53564', 'No'),
(65, 'Steve', 'Lunga', '12 Radar Drive ', 313038536, 'lunga@gmail.com', '3233', 'Yes'),
(66, 'Andrea', 'Mes', '12 Radar Drive', 313038536, 'mes@gmail.com', '1', 'Yes'),
(67, 'Warren', 'Edy', '12 Radar Drive', 620072925, 'warrenedy12@gmail.com', '345436', 'Yes'),
(68, 'Duran', 'Moodley', '12 Radar Drive', 9767858, 'warrenedy12@gmail.com', '325667', 'Yes'),
(69, 'Mtho', 'Kuene', '12 Radar Drive', 620072925, 'warrenedy12@gmail.com', '3456', 'Yes'),
(70, 'Andrea', 'Mes', '12 Radar Drive', 620072925, 'warrenedy12@gmail.com', '4567', 'Yes'),
(71, 'Sipho', 'Kunene', 'Baby House lalucia   ', 1234567891, 'dy97@gmail.com', '1', 'No'),
(72, 'Steve', 'Smith', '12 Radar Drive', 1234567891, 'smith@gmail.com', '123', 'Yes'),
(73, 'Anthony', 'Naido', 'Mountcoumbe drive', 716773493, 'annaido@gmail.com', '6', 'No'),
(74, 'Duran', 'Moodley', 'Chatsworth', 716773493, 'duranmoodley97@gmail.com', 'p10', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_staff`
--

CREATE TABLE `tbl_staff` (
  `staffID` int(11) NOT NULL,
  `duties` varchar(50) NOT NULL,
  `staffImage` blob NOT NULL,
  `personID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_staff`
--

INSERT INTO `tbl_staff` (`staffID`, `duties`, `staffImage`, `personID`) VALUES
(89923, 'Cleaning', 0x6169725f706c616e655f616972706f72742e706e67, 63);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_volunteer`
--

CREATE TABLE `tbl_volunteer` (
  `volunteerID` int(11) NOT NULL,
  `imageID` blob NOT NULL,
  `PersonID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_volunteer`
--

INSERT INTO `tbl_volunteer` (`volunteerID`, `imageID`, `PersonID`) VALUES
(37, 0x626f6f6b732e6a7067, 62),
(38, 0x315f5b6672656569636f6e737765622e6e65745d5f32353139362e706e67, 65),
(39, 0x6169725f706c616e655f616972706f72742e706e67, 66),
(40, 0x48616e676d616e5f62795f6c6567696f6e697374612e6a7067, 67),
(41, 0x315f5b6672656569636f6e737765622e6e65745d5f32353139362e706e67, 68),
(42, 0x696d616765732e6a7067, 69),
(43, 0x626f6f6b7369636f6e2e706e67, 70),
(44, 0x67696674732e706e67, 72),
(45, 0x7465616d2e706e67, 74);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_volunteer_attendance`
--

CREATE TABLE `tbl_volunteer_attendance` (
  `vaID` int(5) NOT NULL,
  `amountOfTimeWorked` int(5) NOT NULL,
  `checkInDate` varchar(10) NOT NULL,
  `checkInTime` time(1) NOT NULL,
  `checkOutTime` time(1) NOT NULL,
  `volunteerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_volunteer_attendance`
--

INSERT INTO `tbl_volunteer_attendance` (`vaID`, `amountOfTimeWorked`, `checkInDate`, `checkInTime`, `checkOutTime`, `volunteerID`) VALUES
(18, 1, '10/31/2016', '21:19:00.0', '21:20:00.0', 37),
(22, 60, '31 Oct 201', '22:13:00.0', '23:13:00.0', 45),
(23, 60, '31 Oct 201', '22:22:00.0', '23:22:00.0', 45);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_adopters`
--
ALTER TABLE `tbl_adopters`
  ADD PRIMARY KEY (`adopterID`),
  ADD UNIQUE KEY `personID` (`personID`),
  ADD UNIQUE KEY `babyID` (`babyID`);

--
-- Indexes for table `tbl_baby`
--
ALTER TABLE `tbl_baby`
  ADD PRIMARY KEY (`babyID`),
  ADD UNIQUE KEY `personID` (`personID`);

--
-- Indexes for table `tbl_blog`
--
ALTER TABLE `tbl_blog`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_donors`
--
ALTER TABLE `tbl_donors`
  ADD PRIMARY KEY (`donorID`),
  ADD UNIQUE KEY `personID` (`personID`);

--
-- Indexes for table `tbl_event`
--
ALTER TABLE `tbl_event`
  ADD PRIMARY KEY (`eventID`);

--
-- Indexes for table `tbl_people`
--
ALTER TABLE `tbl_people`
  ADD PRIMARY KEY (`personID`);

--
-- Indexes for table `tbl_staff`
--
ALTER TABLE `tbl_staff`
  ADD PRIMARY KEY (`staffID`),
  ADD UNIQUE KEY `personID` (`personID`);

--
-- Indexes for table `tbl_volunteer`
--
ALTER TABLE `tbl_volunteer`
  ADD PRIMARY KEY (`volunteerID`),
  ADD KEY `PersonID` (`PersonID`);

--
-- Indexes for table `tbl_volunteer_attendance`
--
ALTER TABLE `tbl_volunteer_attendance`
  ADD PRIMARY KEY (`vaID`),
  ADD KEY `volunteerID` (`volunteerID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_adopters`
--
ALTER TABLE `tbl_adopters`
  MODIFY `adopterID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tbl_baby`
--
ALTER TABLE `tbl_baby`
  MODIFY `babyID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `tbl_blog`
--
ALTER TABLE `tbl_blog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `tbl_donors`
--
ALTER TABLE `tbl_donors`
  MODIFY `donorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `tbl_event`
--
ALTER TABLE `tbl_event`
  MODIFY `eventID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tbl_people`
--
ALTER TABLE `tbl_people`
  MODIFY `personID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;
--
-- AUTO_INCREMENT for table `tbl_staff`
--
ALTER TABLE `tbl_staff`
  MODIFY `staffID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89924;
--
-- AUTO_INCREMENT for table `tbl_volunteer`
--
ALTER TABLE `tbl_volunteer`
  MODIFY `volunteerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT for table `tbl_volunteer_attendance`
--
ALTER TABLE `tbl_volunteer_attendance`
  MODIFY `vaID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_adopters`
--
ALTER TABLE `tbl_adopters`
  ADD CONSTRAINT `tbl_adopters_ibfk_1` FOREIGN KEY (`babyID`) REFERENCES `tbl_people` (`personID`);

--
-- Constraints for table `tbl_baby`
--
ALTER TABLE `tbl_baby`
  ADD CONSTRAINT `tbl_baby_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `tbl_people` (`personID`);

--
-- Constraints for table `tbl_donors`
--
ALTER TABLE `tbl_donors`
  ADD CONSTRAINT `tbl_donors_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `tbl_people` (`personID`);

--
-- Constraints for table `tbl_staff`
--
ALTER TABLE `tbl_staff`
  ADD CONSTRAINT `tbl_staff_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `tbl_people` (`personID`);

--
-- Constraints for table `tbl_volunteer`
--
ALTER TABLE `tbl_volunteer`
  ADD CONSTRAINT `fk_people` FOREIGN KEY (`PersonID`) REFERENCES `tbl_people` (`personID`);

--
-- Constraints for table `tbl_volunteer_attendance`
--
ALTER TABLE `tbl_volunteer_attendance`
  ADD CONSTRAINT `fk_vol` FOREIGN KEY (`volunteerID`) REFERENCES `tbl_volunteer` (`volunteerID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
