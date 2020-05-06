(function(){

    const fetchFollowingImages = () => {
        let followingList = JSON.parse(window.sessionStorage.getItem("user"))
            .followers[0].followingList.map(follower => follower.username);

/*        followingList.map(following => {
            fetch("/retrieveImages/" + following)
                .then(response => Promise.all([response.ok, response.ok ? response.json() : response.text(), response.headers]))
                .then(([ok, body, headers]) => {
                    if (ok) {
                        createFeed(body);
                    }else{
                        console.log(headers);
                    }
                });
        })*/

        fetch("/getfollowingImages")
            .then(r => r.json())
            .then(data => {
                for(let img of data){
                    createFeed(img);
                }
            })

    };

    const createFeed = (imageObject) => {
        let image = imageObject;
        let div = document.createElement("div");
        let p = document.createElement("p");
        p.innerText = image.username;
        div.appendChild(p);
        let img = document.createElement("img");
        img.src = `${window.location.origin}${image.downloadPath}`;
        img.alt = "a great image!";
        img.classList.add("feed-img");
        div.classList.add("feed-div");
        div.appendChild(img);
       $("#mainwrapper").appendChild(div);
    };

    createHeader();
    fetchFollowingImages();
})();

