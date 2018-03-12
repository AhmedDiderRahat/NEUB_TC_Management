-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2017 at 07:00 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `neub_tourist_club`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `admin_type` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `user_name`, `admin_type`, `password`) VALUES
(1, 'root', 'main_admin', 'admin'),
(2, 'neub', 'main_admin', '123'),
(5, 'kuduus', 'main_admin', 'jhhgfryu'),
(9, 'rahat', 'front_admin', 'kuddus_mia');

-- --------------------------------------------------------

--
-- Table structure for table `club_fund`
--

CREATE TABLE IF NOT EXISTS `club_fund` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(50) NOT NULL,
  `reason` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `club_fund`
--

INSERT INTO `club_fund` (`account_id`, `date`, `reason`, `type`, `amount`) VALUES
(1, '07/Dec/2016', 'rahat vai', 'Expense', 1000),
(2, '07/Sep/2016', 'tour', 'Income', 1000),
(5, '04/Dec/2016', 'bijoy dibos', 'Income', 4000),
(11, '06/Jun/2016', 'rahat2', 'Expense', 2001),
(12, '15/Jan/2017', 'member fee', 'Income', 200);

-- --------------------------------------------------------

--
-- Table structure for table `events_information`
--

CREATE TABLE IF NOT EXISTS `events_information` (
  `ev_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `start_date` varchar(20) NOT NULL,
  `end_date` varchar(20) NOT NULL,
  `type` varchar(15) NOT NULL,
  `total_capacity` int(11) NOT NULL,
  `fee` int(11) NOT NULL,
  `spot_id` varchar(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`ev_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `events_information`
--

INSERT INTO `events_information` (`ev_id`, `title`, `start_date`, `end_date`, `type`, `total_capacity`, `fee`, `spot_id`, `status`) VALUES
(1, 'Tour is a touur', '13/Jan/2017', '21/Jan/2017', 'Local', 67, 76, '+1+3', 1),
(2, 'Tour is not only a fun but a passion', '13/Jan/2017', '20/Jan/2017', 'National', 30, 1000, '+4+5+1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `executive_member`
--

CREATE TABLE IF NOT EXISTS `executive_member` (
  `em_id` int(11) NOT NULL AUTO_INCREMENT,
  `designation` varchar(80) NOT NULL,
  `year` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `gm_id` int(11) NOT NULL,
  PRIMARY KEY (`em_id`),
  KEY `general_member_executive_member_fk` (`gm_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `executive_member`
--

INSERT INTO `executive_member` (`em_id`, `designation`, `year`, `status`, `gm_id`) VALUES
(1, 'members', '15-19', 'Previous', 1),
(5, 'VP', '13-14', 'Previous', 2),
(7, 'df', '12-12', 'Current', 4),
(8, 'vp', '14-15', 'Previous', 5);

-- --------------------------------------------------------

--
-- Table structure for table `executive_member_fee`
--

CREATE TABLE IF NOT EXISTS `executive_member_fee` (
  `ef_id` int(11) NOT NULL AUTO_INCREMENT,
  `gm_id` varchar(50) NOT NULL,
  `year` varchar(5) NOT NULL,
  `jan` tinyint(1) DEFAULT NULL,
  `feb` tinyint(1) DEFAULT NULL,
  `mar` tinyint(1) DEFAULT NULL,
  `apr` tinyint(1) DEFAULT NULL,
  `may` tinyint(1) DEFAULT NULL,
  `jun` tinyint(1) DEFAULT NULL,
  `jul` tinyint(1) DEFAULT NULL,
  `aug` tinyint(1) DEFAULT NULL,
  `sep` tinyint(1) DEFAULT NULL,
  `oct` tinyint(1) DEFAULT NULL,
  `nov` tinyint(1) DEFAULT NULL,
  `decm` tinyint(1) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`ef_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `executive_member_fee`
--

INSERT INTO `executive_member_fee` (`ef_id`, `gm_id`, `year`, `jan`, `feb`, `mar`, `apr`, `may`, `jun`, `jul`, `aug`, `sep`, `oct`, `nov`, `decm`, `amount`) VALUES
(12, '5', '2016', 0, 1, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 0, 0, 50),
(13, '1', '2017', 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 70),
(14, '2', '2015', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 100);

-- --------------------------------------------------------

--
-- Table structure for table `general_member`
--

CREATE TABLE IF NOT EXISTS `general_member` (
  `gm_id` int(11) NOT NULL,
  `student_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `admin_id` int(11) NOT NULL,
  PRIMARY KEY (`gm_id`),
  KEY `admin_general_member_fk` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `general_member`
--

INSERT INTO `general_member` (`gm_id`, `student_id`, `name`, `phone`, `admin_id`) VALUES
(1, '140203020101', 'Rahat', '+8801920135759', 1),
(2, '130101010102', 'Kuddus Mia', '+8801920135759', 1),
(3, '140203020109', 'Shorif Uddin', '+8801920135780', 2),
(4, '140203020132', 'Farash Uddin', '+8801792198978', 1),
(5, '130201010008', 'mr. muhit mia', '+8801792198974', 1),
(6, '130303020005', 'pritom Prits', '+8801711164942', 1);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `message` varchar(320) NOT NULL,
  `admin_id` int(11) NOT NULL,
  PRIMARY KEY (`msg_id`),
  KEY `admin_message_fk` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `registration_for_tour`
--

CREATE TABLE IF NOT EXISTS `registration_for_tour` (
  `reg_id` int(11) NOT NULL AUTO_INCREMENT,
  `gm_id` int(11) NOT NULL,
  `ev_id` int(11) NOT NULL,
  `payment` int(11) NOT NULL,
  `guest` tinyint(1) NOT NULL,
  `admin_id` int(11) NOT NULL,
  PRIMARY KEY (`reg_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `registration_for_tour`
--

INSERT INTO `registration_for_tour` (`reg_id`, `gm_id`, `ev_id`, `payment`, `guest`, `admin_id`) VALUES
(1, 2, 2, 800, 0, 1),
(2, 6, 2, 700, 0, 9),
(3, 5, 2, 900, 0, 1),
(4, 3, 2, 1000, 0, 1),
(5, 4, 2, 300, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `spot`
--

CREATE TABLE IF NOT EXISTS `spot` (
  `spot_id` int(11) NOT NULL AUTO_INCREMENT,
  `spot_name` varchar(100) NOT NULL,
  `start_date` varchar(50) NOT NULL,
  `end_date` varchar(50) NOT NULL,
  `transport` varchar(100) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`spot_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `spot`
--

INSERT INTO `spot` (`spot_id`, `spot_name`, `start_date`, `end_date`, `transport`, `status`) VALUES
(1, 'Srimongoled', '02/Dec/2016', '03/Dec/2015', 'Bus', 1),
(2, 'Zaflong', '17/Jan/2017', '18/Jan/2017', 'Boat', 0),
(3, 'Tilagor', '20/Jan/2017', '21/Jan/2017', 'Bus', 1),
(4, 'mejortila', '17/Jan/2017', '17/Jan/2017', 'riska', 1),
(5, 'Bondor', '23/Jan/2017', '25/Jan/2017', 'Cars', 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `executive_member`
--
ALTER TABLE `executive_member`
  ADD CONSTRAINT `general_member_executive_member_fk` FOREIGN KEY (`gm_id`) REFERENCES `general_member` (`gm_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `general_member`
--
ALTER TABLE `general_member`
  ADD CONSTRAINT `admin_general_member_fk` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `admin_message_fk` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
