window.onload = function(){
    $('.register_errors').hide();

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

        console.log(data);
        
        $.ajax({
            type: 'POST',
            url: '/api/register',
            data: data,
            success: function (response) {
                console.log(response);
            }
        });
    });

};