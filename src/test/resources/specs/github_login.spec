@objects
    signin_button   .btn-primary
    login_label     label[for=login_field]
    signin_form     .auth-form-body

= Main section =
    @on desktop
        signin_button:
            css background-color is "rgb(130, 173, 154)"
            width 274px

        signin_form:
            image file img/form_to_be.png, error 1%

        login_label:
            text is "Username or email address"

    @on mobile
        signin_button:
            css background-color is "rgb(130, 173, 154)"
            width 274px

        login_label:
            text is "Username or email address"

        hamburger_menu:
            visible