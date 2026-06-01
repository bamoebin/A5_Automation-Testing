Feature: Logout

  @logout
  Scenario: Logout pelajar
    Given user sudah login sebagai pelajar
    When user membuka menu akun
    And user menekan tombol Keluar
    Then user kembali ke halaman login
