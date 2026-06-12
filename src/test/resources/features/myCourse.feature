Feature: Kursus Saya

  @TC-FR05-01 @Farras
  Scenario: Verifikasi pesan 'Belum ada kursus yang sedang dijalani' tampil pada Tab Dalam Progres
    Given pengguna berhasil login sebagai Pelajar dengan akun farras
    And kondisi pelajar belum mendaftar kursus apapun atau semua kursus yang diikuti sudah berstatus selesai
    When user login dengan akun Pelajar dan klik menu "Kursus Saya" pada navigasi
    And halaman berada pada Tab "Dalam Progres" sebagai tab default
    And amati konten yang tampil
    Then halaman Tab Dalam Progres menampilkan pesan "Belum ada kursus yang sedang dijalani"

  @TC-FR05-04 @Satria
  Scenario: Tab Selesai kosong
    Given user sudah login sebagai pelajar untuk melihat tab selesai
    When user membuka menu Kursus Saya
    And user membuka tab Selesai
    Then muncul pesan kosong kursus selesai

  @TC-FR05-03 @Nieto @MyCourse
  Scenario: Verifikasi tampilan kartu kursus dengan Progress Bar sebagian (1–99%) pada Tab Dalam Progres
    Given pengguna berhasil login sebagai Pelajar dengan username "barba@example.com" dan password "persib123"
    And pelajar sudah mengakses sebagian materi/quiz pada kursus (progress > 0% dan < 100%)
    When pelajar klik menu "Kursus Saya" pada navigasi
    And halaman berada pada Tab "Dalam Progres"
    And pelajar menyelesaikan sebagian Task/Modul/Quiz/Video (jangan diselesaikan seluruhnya)
    And pelajar mengamati kartu kursus yang tampil
    Then kartu kursus tampil pada Tab Dalam Progres
    And kartu kursus menampilkan komponen: Gambar kursus, Nama kursus, Nama pengajar, Progress Bar terisi sebagian (nilai persentase > 0% dan < 100%)
    And kursus TIDAK muncul di Tab Selesai

  @TC-FR05-05 @Nieto @MyCourse
  Scenario: Verifikasi tampilan kartu kursus dengan Progress Bar 100% (kursus selesai) pada Tab Selesai
    Given pengguna berhasil login sebagai Pelajar dengan username "barba@example.com" dan password "persib123"
    And pelajar sudah menyelesaikan SELURUH materi dan quiz pada minimal 1 kursus (progress = 100%)
    When pelajar klik menu "Kursus Saya" pada navigasi
    And pelajar menyelesaikan seluruh Quiz/Modul/Video hingga progress bar = 100%
    And pelajar klik Tab "Selesai"
    And pelajar mengamati kartu kursus yang tampil
    Then kartu kursus tampil pada Tab Selesai
    And kartu kursus menampilkan komponen: Gambar kursus, Nama kursus, Nama pengajar, Progress Bar penuh (100%)
    And Tab Selesai TIDAK menampilkan empty state
