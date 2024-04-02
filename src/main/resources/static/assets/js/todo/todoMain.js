new Vue({
    el: '#App',
    data: {
        todoObj: {
            itemDescription: '',
            status: 'PENDING',
            id: 0,
            user: '',
        },
        toDoList: [],

        toDoUrl: "todolist",
        logoutUrl: "/logOut",
    },

    watch: {
        // code here
    },

    computed: {
        // code here
    },

    methods: {
        logout(){

            axios.get(this.logoutUrl)
                .then(response => {
                    if (response.status === 200) {
                        toastr.success("Awesome!", "Completed");
                        window.location.reload()
                    } else {
                        toastr.error("Eish !", "Failed");
                    }
                }).catch(err => {
                    toastr.error("Eish !", "Failed");})
        },
        markTask(value, todo) {

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


            let status = "DONE";
            const isChecked = event.target.checked;
            if (isChecked != 1) {
                status = "PENDING"
            }


            todo.status = status;
            todo.user = this.getUser();

            axios.post(this.toDoUrl, todo)
                .then(response => {
                    if (response.data.id) {
                        toastr.success("Awesome!", "Logged Out");
                    } else {
                        toastr.error("Eish !", "Failed");
                    }
                }).catch(err => {
                toastr.error("Eish !", "Failed");
            })

        },
        deleteToDo(todo) {

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


            axios.get(this.toDoUrl + '/delete/' + todo.id.toString())
                .then(response => {
                    toastr.success("Success !", "Deleted Task");
                    this.getToDoList();
                }).catch(err => {
                toastr.error("Eish !", "Failed");
            })

        },

        getUser: function () {
            return $("#userName").text();
        }, submitToDo() {
            this.todoObj.user = this.getUser();
            if (this.todoObj.itemDescription === '') {
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
                        axios.post(this.toDoUrl, this.todoObj)
                            .then(response => {
                                if (response.data.id) {
                                    Swal.fire(
                                        'Success!',
                                        response.data.message,
                                        'success'
                                    ).then(function () {
                                        location.reload();
                                    });

                                } else {
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
$(document).ready(function() {
    const buttonA = $('#buttonA');
    const buttonB = $('#buttonB');

    buttonA.hover(function(){
        buttonA.removeClass("Myvisible").addClass("Myhidden");
        buttonB.removeClass("Myhidden").addClass("Myvisible");
    });
    buttonB.mouseleave(function(){
        buttonA.removeClass("Myhidden").addClass("Myvisible");
        buttonB.removeClass("Myvisible").addClass("Myhidden");
    });

});