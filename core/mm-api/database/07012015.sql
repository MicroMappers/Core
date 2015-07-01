-- phpMyAdmin SQL Dump
-- version 3.5.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 01, 2015 at 10:21 AM
-- Server version: 5.5.29
-- PHP Version: 5.4.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mm_scheduler`
--
CREATE DATABASE `mm_scheduler` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `mm_scheduler`;

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `clientID` int(20) NOT NULL AUTO_INCREMENT,
  `aidrUserID` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `hostURL` varchar(200) NOT NULL,
  `hostAPIKey` varchar(200) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `queueSize` int(10) unsigned NOT NULL DEFAULT '50',
  `aidrHostURL` varchar(100) DEFAULT NULL,
  `defaultTaskRunsPerTask` int(10) unsigned NOT NULL DEFAULT '3',
  `status` int(10) unsigned NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `clientID_UNIQUE` (`clientID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `clientApp`
--

CREATE TABLE `clientApp` (
  `clientAppID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `clientID` bigint(20) unsigned NOT NULL,
  `crisisID` bigint(20) unsigned DEFAULT NULL,
  `nominalAttributeID` bigint(20) DEFAULT NULL,
  `platformAppID` bigint(20) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `shortName` varchar(50) NOT NULL,
  `taskRunsPerTask` int(10) unsigned NOT NULL DEFAULT '1',
  `quorum` int(10) unsigned NOT NULL DEFAULT '1',
  `iconURL` varchar(200) DEFAULT 'http://i.imgur.com/lgZAWIc.png',
  `status` int(11) unsigned NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `appType` int(11) DEFAULT '1',
  `isCustom` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`clientAppID`),
  UNIQUE KEY `shortName_UNIQUE` (`shortName`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=265 ;

-- --------------------------------------------------------

--
-- Table structure for table `clientAppAnswer`
--

CREATE TABLE `clientAppAnswer` (
  `clientAppID` bigint(20) NOT NULL,
  `answer` text NOT NULL,
  `voteCutOff` int(10) unsigned NOT NULL DEFAULT '2',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `activeAnswerKey` text,
  PRIMARY KEY (`clientAppID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `clientAppDeployment`
--

CREATE TABLE `clientAppDeployment` (
  `deploymentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `clientAppID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`deploymentID`),
  UNIQUE KEY `crisisID_UNIQUE` (`deploymentID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `clientAppEvent`
--

CREATE TABLE `clientAppEvent` (
  `clientAppEventID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `clientAppID` bigint(20) NOT NULL,
  `sequence` int(11) NOT NULL,
  `eventID` bigint(20) NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`clientAppEventID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Table structure for table `clientAppSource`
--

CREATE TABLE `clientAppSource` (
  `clientAppSourceID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `clientAppID` bigint(20) unsigned NOT NULL,
  `sourceURL` varchar(200) NOT NULL,
  `status` int(10) unsigned NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`clientAppSourceID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=73851 ;

-- --------------------------------------------------------

--
-- Table structure for table `crisis`
--

CREATE TABLE `crisis` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `crisisID` bigint(20) DEFAULT NULL,
  `crisisName` varchar(50) DEFAULT NULL,
  `clientAppID` bigint(20) DEFAULT NULL,
  `displayName` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `activationStart` timestamp NULL DEFAULT NULL,
  `activationEnd` timestamp NULL DEFAULT NULL,
  `clickerType` varchar(100) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refreshInMinute` int(11) NOT NULL DEFAULT '60',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Table structure for table `imageMetaData`
--

CREATE TABLE `imageMetaData` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(200) DEFAULT NULL,
  `fileName` varchar(150) NOT NULL,
  `thumbnail` varchar(150) DEFAULT NULL,
  `lat` varchar(100) DEFAULT NULL,
  `lng` varchar(100) DEFAULT NULL,
  `bounds` text,
  `sourceType` int(11) NOT NULL DEFAULT '1',
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3443 ;

-- --------------------------------------------------------

--
-- Table structure for table `markerStyle`
--

CREATE TABLE `markerStyle` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `clientAppID` bigint(20) DEFAULT NULL,
  `appType` varchar(25) NOT NULL,
  `style` text NOT NULL,
  `isDefault` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Table structure for table `reportTemplate`
--

CREATE TABLE `reportTemplate` (
  `reportTemplateID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `clientAppID` bigint(20) unsigned NOT NULL,
  `taskQueueID` bigint(20) NOT NULL,
  `taskID` bigint(20) NOT NULL,
  `tweetID` varchar(500) NOT NULL,
  `tweet` text NOT NULL,
  `author` varchar(100) DEFAULT NULL,
  `lat` varchar(100) DEFAULT NULL,
  `lon` varchar(100) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `created` varchar(150) DEFAULT NULL,
  `answer` text NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reportTemplateID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=35885 ;

-- --------------------------------------------------------

--
-- Table structure for table `taskLog`
--

CREATE TABLE `taskLog` (
  `taskQueueID` bigint(20) unsigned NOT NULL,
  `status` int(10) unsigned NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`taskQueueID`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `taskQueue`
--

CREATE TABLE `taskQueue` (
  `taskQueueID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `taskID` bigint(20) DEFAULT NULL,
  `clientAppID` bigint(20) unsigned NOT NULL,
  `documentID` bigint(20) DEFAULT NULL,
  `clientAppSourceID` bigint(20) DEFAULT NULL,
  `status` int(11) unsigned NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`taskQueueID`,`clientAppID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=554962 ;

-- --------------------------------------------------------

--
-- Table structure for table `taskQueueResponse`
--

CREATE TABLE `taskQueueResponse` (
  `taskQueueID` bigint(20) NOT NULL,
  `response` longtext NOT NULL,
  `taskInfo` text,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`taskQueueID`),
  UNIQUE KEY `taskQueueID_UNIQUE` (`taskQueueID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `userToken`
--

CREATE TABLE `userToken` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(36) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
