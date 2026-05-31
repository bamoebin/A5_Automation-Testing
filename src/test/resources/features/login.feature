Feature: Login

  Scenario: Login valid
    Given user berada di halaman login
    When user login dengan email "farras" dan password "farras"
    Then dashboard tampil dengan navbar yang berisi menu utama dan nama akun
