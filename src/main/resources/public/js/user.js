window.onload = function(){
    $('.register_errors').hide();
    $('.login_error').hide();

    const errorlist = {
        "allFieldsRequired": "All fields are required!",
        "passwordMismatch": "Passwords do not match!",
        "tooShortName": "Your name is too short!",
        "invalidName": "Your name contains invalid characters!",
        "emailExists": "Your email already exists!",
        "emailInvalid": "That does not look like a valid email!",
        "couldNotParseAge": "Age could not be parsed!",
        "ageOutsideInterval":"Age is outside the interval!"}
    

    $('.register-button').click(function(event){
        event.preventDefault();
        let data = {
            'email': $('#email').val(),
            'firstName': $('#first_name').val(),
            'lastName': $('#last_name').val(),
            'password': $('#password').val(),
            'passwordAgain': $('#password_again').val(),
            'age': $('#age').val(),
            'gender': $('input[name=gender]:checked').val(),
            'preference': $('input[name=preference]:checked').val()
        };

        $.ajax({
            type: 'POST',
            contentType: 'application/JSON',
            url: '/api/register',
            data: JSON.stringify(data),
            success: function (response) {
                console.log(response)
                $('.errors').empty();
                $('#statusMessages').empty();

                if(JSON.parse(response)["valid"] === true){
                    $('.register_errors').hide();
                    $(location).attr('href', window.location.href + "/personality");
                } else {
                    $.each(JSON.parse(response), function(key, value) {
                        if(value === true) {
                            $('.errors').append("<li>" + errorlist[key] + "</li>");
                        }
                        $('.register_errors').show();
                    });
                }
                },
            error: function(response) {
                console.log(response);
            }
        });
    });

    $('.login-button').click(function(event){
        event.preventDefault();
        let data = {
            'email': $('#login_email').val(),
            'password': $('#login_password').val(),
        };
        
        $.ajax({
            type: 'POST',
            url: '/api/login',
            contentType: 'application/JSON',
            data: JSON.stringify(data),
            success: function (response) {
                if(JSON.parse(response)["valid"] === true){
                    $('#login').modal('hide');
                    $(location).attr('href', window.location.href + "/user/page");
                } else {
                    $('.login_error').show();
                }
            },
            error: function(response) {
                $('.login_error').empty();
                $('.login_error').append("<p>Sorry :( Could not connect to the server.</p>");
                $('.login_error').show();
                console.log(response);
            }
        });
    });
};