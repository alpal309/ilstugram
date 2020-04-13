(function() {

    fetch("/logout").then(r => r.text()).then(r => console.log(r));


    let register = $("#register");

    register.addEventListener("submit", function (event) {
        event.preventDefault();
        let formData = mapFormData(event.target);
        ajax({
            url: "/createUser",
            method: "POST",
            data: formData,
            complete: function(response, location, status){
                if(Math.floor(status/100) < 4) {
                    window.location = location;
                }
                else
                    alert(response);
            }
        });
    });

    let login = $("#login");

    login.addEventListener("submit", function(event){
        event.preventDefault();
        let formData = mapFormData(event.target);

        ajax({
            url: "/login",
            method: "POST",
            data: formData,
            complete: function(response, location, status) {
                if (Math.floor(status / 100) < 4){
                    window.location = location;
                }
                else {
                    alert(response);
                }
            }
        });
    });

    $("#displayloginform").addEventListener("click", function(){
        $(".formdivwrapper").style.left = "0";
    });

    $("#displayregisterform").addEventListener("click", function(){
        $(".formdivwrapper").style.left = "-100%";
    });

})();