-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: fdb13.awardspace.net
-- Generation Time: Aug 15, 2016 at 08:41 PM
-- Server version: 5.7.13-log
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2104290_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_blog`
--

CREATE TABLE `tbl_blog` (
  `blog_id` int(4) NOT NULL,
  `blog_heading` varchar(100) NOT NULL,
  `blog_body` varchar(1000) NOT NULL,
  `blog_pic` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_blog`
--

INSERT INTO `tbl_blog` (`blog_id`, `blog_heading`, `blog_body`, `blog_pic`) VALUES
(1, 'Baby Arrival ', 'Today a new baby came to the baby house , very cute and cubby', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_blog`
--
ALTER TABLE `tbl_blog`
  ADD PRIMARY KEY (`blog_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_blog`
--
ALTER TABLE `tbl_blog`
  MODIFY `blog_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
