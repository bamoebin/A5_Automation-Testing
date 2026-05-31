Feature: Kursus Saya

  Background:
    Given user sudah login

  Scenario: Kursus Saya kosong
    When user membuka menu Kursus Saya
    Then tab Dalam Progres aktif
    And muncul pesan kosong kursus
