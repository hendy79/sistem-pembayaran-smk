DROP DATABASE IF EXISTS `pembayaran_spp`;
CREATE DATABASE `pembayaran_spp`;
USE `pembayaran_spp`;

/*Table structure for table `jurusan` */
CREATE TABLE `jurusan` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nama_jurusan` VARCHAR(30) NOT NULL,
  `biaya_praktikum` INT(11) NOT NULL,
  PRIMARY KEY (`id`)
);

/*Table structure for table `kelas` */
CREATE TABLE `kelas` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nama_kelas` VARCHAR(10) NOT NULL,
  `biaya_gedung` INT(11) NOT NULL,
  PRIMARY KEY (`id`)
);

/*Table structure for table `petugas` */
CREATE TABLE `petugas` (
  `nip` VARCHAR(20) NOT NULL,
  `nohp` VARCHAR(20) NOT NULL,
  `nama` VARCHAR(50) NOT NULL,
  `alamat` VARCHAR(150) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`nip`)
);

/*Table structure for table `siswa` */
CREATE TABLE `siswa` (
  `nis` VARCHAR(20) NOT NULL,
  `nama` VARCHAR(50) NOT NULL,
  `alamat` VARCHAR(150) NOT NULL,
  `jenis_kelamin` VARCHAR(10) NOT NULL,
  `id_kelas` INT(11) NOT NULL,
  `id_jurusan` INT(11) NOT NULL,
  PRIMARY KEY (`nis`),
  KEY `koneksi_jurusan` (`id_jurusan`),
  KEY `koneksi_kelas` (`id_kelas`),
  CONSTRAINT `koneksi_jurusan` FOREIGN KEY (`id_jurusan`) REFERENCES `jurusan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `koneksi_kelas` FOREIGN KEY (`id_kelas`) REFERENCES `kelas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

/*Table structure for table `transaksi` */
CREATE TABLE `transaksi` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nis` VARCHAR(20) NOT NULL,
  `nip` VARCHAR(20) NOT NULL,
  `tanggal` DATE NOT NULL,
  `biaya` INT(11) NOT NULL,
  `jenis_pembayaran` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `koneksi_petugas` (`nip`),
  KEY `koneksi_siswa` (`nis`),
  CONSTRAINT `koneksi_petugas` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `koneksi_siswa` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT  INTO `jurusan`(`id`,`nama_jurusan`,`biaya_praktikum`) VALUES 
(1,'Teknik Kendaraan Ringan',30000),
(2,'Akuntansi',30000),
(3,'Farmasi',40000),
(4,'Teknik Sepeda Motor',30000);

/*Data for the table `kelas` */

INSERT  INTO `kelas`(`id`,`nama_kelas`,`biaya_gedung`) VALUES 
(1,'1',630000),
(2,'2',500000),
(3,'3',300000);

/*Data for the table `petugas` */

INSERT  INTO `petugas`(`nip`,`nohp`,`nama`,`alamat`,`password`) VALUES 
('12345','0836481231','Adi','Duren Sawit, Jakarta Timur','adi_1234'),
('34512','089973418464','Cintia','Balikpapan, Kalimantan','cin%pass'),
('54321','0876323784','Budi','Maliboro, Jawa Tengah','admin_budi!');

/*Data for the table `siswa` */

INSERT INTO siswa VALUES
('14759','Merry','Kelapa Gading, Jakarta Utara', 'Perempuan', 2, 4),
('32434','Budiman', 'Balikpapan, Kalimantan', 'Lelaki', 3, 2),
('49739','Alda','Duren Sawit, Jakarta Timur', 'Perempuan', 2, 3),
('84773','Khalique','Kep.Seribu, Jakarta', 'Perempuan', 1, 1);