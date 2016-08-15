-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 10, 2016 at 07:35 AM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `baby_house_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_adopters`
--

DROP TABLE IF EXISTS `tbl_adopters`;
CREATE TABLE IF NOT EXISTS `tbl_adopters` (
  `adopterID` varchar(4) NOT NULL,
  `adopter_fname` varchar(30) NOT NULL,
  `adopter_lname` varchar(30) NOT NULL,
  `baby_id` int(4) NOT NULL,
  `date_adopted` date NOT NULL,
  `date_registered` date NOT NULL,
  `archived_parent` char(1) NOT NULL,
  PRIMARY KEY (`adopterID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_baby`
--

DROP TABLE IF EXISTS `tbl_baby`;
CREATE TABLE IF NOT EXISTS `tbl_baby` (
  `baby_id` varchar(4) NOT NULL,
  `baby_name` varchar(30) NOT NULL,
  `baby_gender` varchar(20) NOT NULL,
  `baby_race` varchar(20) NOT NULL,
  `date_arrived` date NOT NULL,
  `date_exited` date NOT NULL,
  `baby_status` varchar(20) NOT NULL,
  PRIMARY KEY (`baby_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_blog`
--

DROP TABLE IF EXISTS `tbl_blog`;
CREATE TABLE IF NOT EXISTS `tbl_blog` (
  `blog_id` int,
  `blog_heading` varchar(100) NOT NULL,
  `blog_body` varchar(1000) NOT NULL,
  `blog_pic` int(3) NULL,
PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_donations`
--

DROP TABLE IF EXISTS `tbl_donations`;
CREATE TABLE IF NOT EXISTS `tbl_donations` (
  `donation_ID` varchar(4) DEFAULT NULL,
  `donor_name` int(50) NOT NULL,
  `contact_details` int(10) NOT NULL,
  `amount_donated` int(6) NOT NULL,
  `donor_email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_events`
--

DROP TABLE IF EXISTS `tbl_events`;
CREATE TABLE IF NOT EXISTS `tbl_events` (
  `event_id` int(4) NOT NULL,
  `event_date` date NOT NULL,
  `event_title` varchar(50) NOT NULL,
  `event_description` varchar(300) NOT NULL,
  `image` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_home`
--

DROP TABLE IF EXISTS `tbl_home`;
CREATE TABLE IF NOT EXISTS `tbl_home` (
  `home_id` varchar(4) NOT NULL,
  `h_Description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_people`
--

DROP TABLE IF EXISTS `tbl_people`;
CREATE TABLE IF NOT EXISTS `tbl_people` (
  `person_fname` varchar(30) NOT NULL,
  `person_lname` varchar(30) NOT NULL,
  `person_address` varchar(40) NOT NULL,
  `contactnum` int(10) NOT NULL,
  `person_email` varchar(30) NOT NULL,
  `adopterID` varchar(4) NOT NULL,
  `baby_id` varchar(4) NOT NULL,
  `staff_id` varchar(4) NOT NULL,
  `volunteerID` varchar(4) NOT NULL,
  `Person_id` varchar(4) NOT NULL,
  `donation_ID` varchar(4) NOT NULL,
  PRIMARY KEY (`Person_id`),
  KEY `fk_staffid` (`staff_id`),
  KEY `fk_adopterid` (`adopterID`),
  KEY `fk_babyid` (`baby_id`),
  KEY `volunteerID` (`volunteerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_staff`
--

DROP TABLE IF EXISTS `tbl_staff`;
CREATE TABLE IF NOT EXISTS `tbl_staff` (
  `staff_id` varchar(4) NOT NULL,
  `staff_fname` varchar(30) NOT NULL,
  `staff_lname` varchar(30) NOT NULL,
  `shift_time` time NOT NULL,
  `salary_per_hour` int(3) NOT NULL,
  `duties` varchar(30) NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_volunteers`
--

DROP TABLE IF EXISTS `tbl_volunteers`;
CREATE TABLE IF NOT EXISTS `tbl_volunteers` (
  `volunteerID` varchar(4) NOT NULL,
  `volunteer_fname` varchar(30) NOT NULL,
  `volunteer_lname` varchar(30) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `hours_worked` int(11) NOT NULL,
  `img_id` int(11) NOT NULL,
  PRIMARY KEY (`volunteerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_wishlist`
--

DROP TABLE IF EXISTS `tbl_wishlist`;
CREATE TABLE IF NOT EXISTS `tbl_wishlist` (
  `wish_id` int(11) NOT NULL,
  `wish_title` int(11) NOT NULL,
  `wish_description` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_people`
--
ALTER TABLE `tbl_people`
  ADD CONSTRAINT `fk_adopterid` FOREIGN KEY (`adopterID`) REFERENCES `tbl_adopters` (`adopterID`),
  ADD CONSTRAINT `fk_babyid` FOREIGN KEY (`baby_id`) REFERENCES `tbl_baby` (`baby_id`),
  ADD CONSTRAINT `fk_staffid` FOREIGN KEY (`staff_id`) REFERENCES `tbl_staff` (`staff_id`),
  ADD CONSTRAINT `tbl_people_ibfk_1` FOREIGN KEY (`volunteerID`) REFERENCES `tbl_volunteers` (`volunteerID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
