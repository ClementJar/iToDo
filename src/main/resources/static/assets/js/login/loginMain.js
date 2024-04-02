new Vue({
    el: '#App',
    data: {
        userDetails: {
            username: '',
            email: '',
            password: '',
        },
        password2: '',
        credentials: {
            username: '',
            password: '',
        },
        toDoList: [],

        signInUrl: "/home/login",
        registerUrl: "/home/register",
    },

    watch: {
        // code here
    },

    computed: {
        // code here
    },

    methods: {
        signIn() {

            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            };

            axios.post(this.signInUrl,  'username='+
                this.credentials.username + '&password='+this.credentials.password,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                .then(response => {
                    if (response.status === 200 && !response.request.responseURL.includes("?error")) {
                        toastr.success("Awesome!", "Completed");
                        window.location.href = response.request.responseURL;
                    } else if (response.message === "unregistered") {
                        toastr.info("Oops!", "Please Register to continue");
                        _showForm('signup');
                    } else {
                        toastr.error("Eish !", "Failed to Login");
                    }
                })

        },
        submitSignUp() {
            if (this.userDetails.password !== this.password2) {
                Swal.fire('Password Mismatch', 'Type the same password in both password fields', 'info');
                return;
            } else if (this.userDetails.email === '' || this.userDetails.password === '' || this.password2 === '') {
                Swal.fire('Missing Data', 'Description is required.', 'info');
                return;
            } else {

                Swal.fire(
                    {
                        title: 'Confirm',
                        text: 'Are you sure you want to proceed with submission?',
                        icon: 'question',
                        showCancelButton: true,
                    }).then(r => {
                    if (r.isConfirmed) {
                        axios.post(this.registerUrl, this.userDetails)
                            .then(response => {
                                if (response.data.message === "success") {
                                    Swal.fire(
                                        'Success!',
                                        "Successful Registration. You can Now Login",
                                        'success'
                                    ).then(function () {
                                        _showForm('signin');
                                    });

                                }else if(response.data.message === "EmailExists") {
                                    Swal.fire(
                                        'Oops!',
                                        "Email in Use. If this is your email, proceed to Login",
                                        'info'
                                    ).then(function () {

                                    });
                                }else {
                                    Swal.fire('Oops error!', response.data.message, 'error')
                                }
                            }).catch(err => {
                            Swal.fire('Oops error!', err.message, 'error')
                        })
                    }
                })
            }
        },

        getToDoList() {
            axios.get(this.toDoUrl)
                .then(resp => {
                    this.toDoList = resp.data
                })
                .catch(err => {
                    console.log('error', err.message)
                })
        },

    },

    created() {
        this.getToDoList();
    },

    mounted() {

    },
});
