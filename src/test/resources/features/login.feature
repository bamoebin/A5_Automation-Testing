Feature: Login

  @Farras
  Scenario: Login valid
    Given user berada di halaman login
    When user login dengan email "farras" dan password "farras"
    Then dashboard tampil dengan navbar yang berisi menu utama dan nama akun

  @TC-1.2.2 @Nieto
  Scenario: Verifikasi login gagal dengan username yang tidak terdaftar
    Given pengguna telah berada di halaman login JTK Learn
    When pengguna mengisi field username dengan username tidak terdaftar "userTidakAda@example.com"
    And pengguna mengisi field password dengan "salahPassword123"
    And pengguna menekan tombol Login
    Then sistem tidak mengarahkan pengguna ke halaman dashboard
    And sistem menampilkan notifikasi login gagal