Feature: Kursus Saya

  Scenario: Kursus Saya kosong
    Given user sudah login
    When user membuka menu Kursus Saya
    Then tab Dalam Progres aktif
    And muncul pesan kosong kursus

  @TC-FR05-04
  Scenario: Tab Selesai kosong
    Given user sudah login sebagai pelajar untuk melihat tab selesai
    When user membuka menu Kursus Saya
    And user membuka tab Selesai
    Then muncul pesan kosong kursus selesai
