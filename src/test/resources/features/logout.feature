Feature: Logout

  @logout @Satria
  Scenario: Melakukan logout untuk sesi Pelajar
    Given User sudah login dengan role Pelajar
    When User menekan drop down Nama Akun
    And User menekan tombol "Keluar"
    Then User kembali ke halaman login
