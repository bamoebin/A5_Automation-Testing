Feature: Login

  @1.2.1 @Farras
  Scenario: Login valid sebagai Pelajar
    Given user belum login
    And user membuka alamat situs JTK Learn
    And tersedia akun pelajar terdaftar
    When user mengisi field Email dengan email pelajar valid "farras@example.com"
    And user mengisi field Kata Sandi dengan password yang benar "bamoebin123"
    And user klik tombol "Masuk"
    And user cek header navigasi
    Then dashboard tampil dengan navbar yang berisi menu utama dan nama akun

  @TC-1.2.2 @Nieto @Login
  Scenario: Verifikasi login gagal dengan username yang tidak terdaftar
    Given pengguna telah berada di halaman login JTK Learn
    When pengguna mengisi field username dengan username tidak terdaftar "userTidakAda@example.com"
    And pengguna mengisi field password dengan "salahPassword123"
    And pengguna menekan tombol Login
    Then sistem tidak mengarahkan pengguna ke halaman dashboard
    And sistem menampilkan notifikasi login gagal