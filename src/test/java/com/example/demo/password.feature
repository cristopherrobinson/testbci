Feature: Validar política de contraseñas

  Background:
    * def validatePassword =
    """
    function(password) {
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{6,15}$/;
        return regex.test(password);
    }
    """

  Scenario: Validar política de contraseña exitosa
    * def result = validatePassword('Socius777')
    * assert result == true

  Scenario: Validar política de contraseña fallida por falta de letra mayúscula
    * def result = validatePassword('socius777')
    * assert result == false

