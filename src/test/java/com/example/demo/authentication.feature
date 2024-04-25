Feature: Test de endpoints de usuario que requieren autenticación JWT

  Background:
    * url 'http://localhost:8080'
    Given path 'auth/login'
    And request { username: 'ccanoles@bci.cl', password: 'somosSocius123' }
    When method post
    Then status 200
    And match response contains { token: '#notnull' }
    And def token = response.token
    * print 'Token JWT obtenido:', token

  Scenario: Login y obtener token JWT
    And match token contains 'ey'

  @success
  Scenario: Agregar un usuario con token JWT
    Given path 'usuario/agregar'
    And header Authorization = 'Bearer ' + token
    And request 
    """
    {
      "name": "Cristopher Cañoles",
      "email": "cris@bci.cl",
      "password": "somosSocius123",
      "phones": [
        {
          "number": "932677322",
          "citycode": "56",
          "countrycode": "0"
        }
      ]
    }
    """
    When method post
    Then status 201
    And match response contains { isactive: true }
  
  @failure
  Scenario: Intentar agregar un usuario con un email ya registrado
    Given path 'usuario/agregar'
    And header Authorization = 'Bearer ' + token
    And request 
    """
    {
      "name": "Cristopher Cañoles",
      "email": "cris@bci.cl",
      "password": "somosSocius123",
      "phones": [
        {
          "number": "932677322",
          "citycode": "56",
          "countrycode": "0"
        }
      ]
    }
    """
    When method post
    Then status 400
    And match response contains { description: 'El email ya se encuentra registrado: cris@bci.cl' }

  Scenario: Buscar un usuario con token JWT
    Given path 'usuario/buscar'
    And header Authorization = 'Bearer ' + token
    And request { "email": "cris@bci.cl" }
    When method post
    Then status 200
    And match response contains { email: 'cris@bci.cl' }

  Scenario: Buscar un usuario no existente con token JWT
    Given path 'usuario/buscar'
    And header Authorization = 'Bearer ' + token
    And request { "email": "cris2@bci.cl" }
    When method post
    Then status 404
    And match response contains { description: 'Usuario no encontrado con email: cris2@bci.cl' }