const $ = (el, forceArray) => {
	const ele = document.querySelectorAll(el);
	return (ele.length === 1 && !forceArray ? ele[0] : ele);
};

const mapFormData = (form) =>{
	return Array.from(form.elements).map(value => {
		return {[value.getAttribute("name")] : value.value};
	}).filter(el => Object.keys(el)[0] !== "null");
};

const ajax = (o) => {
	let xhr = new XMLHttpRequest();

	if(o.events){
		//loadstart, load, loadend, progress (upload/download only), error, abort
		for(let [key, value] of Object.entries(o.events)){
			xhr.addEventListener(key, value);
		}
	}

	xhr.onreadystatechange = function(){
		if(o.progress)
			o.progress((this.readyState*25));
		if(this.readyState === XMLHttpRequest.DONE){
			let callback = null;
			switch(Math.floor(xhr.status/100)){
				case 1:
				case 2:
				case 3:
					callback = o.success;
					break;
				case 4:
				case 5:
					callback = o.error;
					break;
				default:
					break;
			}
			let response = (xhr.responseType === "text" ? xhr.responseText : xhr.response);

			if(typeof response == 'string' && (response.charAt(0) === "[" || response.charAt(0) === "{"))
				response = JSON.parse(response);

			if(callback)
				callback(response);

			if(o.complete)
				o.complete(response, xhr.getResponseHeader("Location"), xhr.status);
		}
	};

	if(o.data) {
		var params = typeof o.data == 'string' || o.noEncoding === true || o.isFile === true
			? o.data
			: Object.keys(o.data).map(function (k) {
				return (!o.data.length
						? encodeURIComponent(k) + '=' + encodeURIComponent(o.data[k])
						: encodeURIComponent(Object.keys(o.data[k])) + '=' + encodeURIComponent(Object.values(o.data[k]))
				);
			}).join('&');
	}

	o.method = (o.method ? o.method : "GET");
	xhr.open(o.method, (o.url === undefined ? "/" : o.method === "GET" && o.data !== undefined ? (o.url+"?"+params) : o.url), o.async || true);

	if(o.method !== "GET" && o.useContentType !== false && o.isFile !== true)
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

	if(o.async !== false)
		xhr.responseType = o.responseType || "text";

	if(o.mimeType)
		xhr.overrideMimeType(o.mimeType);

	if(o.headers)
		for(let [key, value] of Object.entries(o.headers)){
			xhr.setRequestHeader(key, value);
		}

	xhr.send(params);
};

const createHeader = () => {
	const parent = $("#navheader");
	let inner = document.createElement("div");
	inner.className = "navheadinner";

	for(let i = 0; i < 3; i++){
		let div = document.createElement("div");
		div.classList.add("header");

		if(i === 0){
			div.classList.add("headerinnerleft");
			let a = document.createElement("a");
			a.href = "/";
			let img = document.createElement("img");
			img.src = "../images/ilstugram.png";
			img.alt = "logo";
			img.classList.add("headerlogo");
			a.appendChild(img);
			div.appendChild(a);
		}else if(i === 1){
			div.classList.add("headerinnermid");
			let search = document.createElement("input");
			search.placeholder = "Search";
			search.type = "text";
			search.className = "input-border";
			div.appendChild(search);
		}else if(i === 2){
			div.classList.add("headerinnerright");

			let a1 = document.createElement("a");
			a1.href = "feed.html";
			let icon1 = document.createElement("i");
			icon1.className = "far fa-newspaper";
			a1.appendChild(icon1);
			div.appendChild(a1);

			let a2 = document.createElement("a");
			a2.href = "profile.html";
			let icon2 = document.createElement("i");
			icon2.className = "far fa-user";
			a2.appendChild(icon2);
			div.appendChild(a2);
		}
		inner.appendChild(div);
		parent.appendChild(inner);
	}

};

const createModal = (imgobj, updateImagesFunction) => {
	//creating containers and exit button
	const modal = document.createElement("div");
	modal.classList.add("modal");
	const inner = document.createElement("div");
	inner.classList.add("modalinner");
	const exit = document.createElement("i");
	exit.className = "fas fa-times exit";
	exit.addEventListener("click", () => {
		modal.style.display = "none";
		while(inner.lastChild){
			inner.removeChild(inner.lastChild);
		}
		document.body.style.overflow = "auto";
		document.body.removeChild(modal);
	});

	//creating the actual image
	const img = document.createElement("img");
	img.alt = "user uploaded image";
	img.src = `${window.location.origin}${imgobj.downloadPath}`;
	img.classList.add("modalimage");
	inner.appendChild(img);

	//creating the container that holds all the image details/comments
	let commentsection = document.createElement("div");
	commentsection.classList.add("commentcontainer");

	//creates the detail section about the image
	let details = document.createElement("div");
	details.className = "imagedetails marg-bottom";
	let actionicons = document.createElement("div");
	actionicons.className = "actionicons";

	//creates the image description section
	let userdetails = document.createElement("div");
	userdetails.className = "userdetails";
	let username = document.createElement("span");
	username.innerHTML = imgobj.username;
	username.className = "modalusername i-right";
	let description = document.createElement("span");
	description.innerHTML = imgobj.description ? imgobj.description : "description goes here";
	userdetails.appendChild(username);
	userdetails.appendChild(description);
	let date = document.createElement("p");
	date.className = "modaldate";
	date.innerHTML = new Date(imgobj.uploadDate).toLocaleDateString();
	userdetails.appendChild(date);

	//creates the likes section
	let heart = document.createElement("i");
	heart.className = "far fa-heart i-click i-right";
	let likes = document.createElement("span");
	likes.innerHTML = "0";
	likes.innerHTML += " likes";

	//throwing all these elements onto the DOM
	actionicons.appendChild(heart);
	actionicons.appendChild(likes);
	details.appendChild(actionicons);
	details.appendChild(userdetails);
	commentsection.appendChild(details);

	//creates the comment box section
	let inputdiv = document.createElement("div");
	inputdiv.className = "commentdiv marg-bottom";
	let input = document.createElement("input");
	input.type = "text";
	input.placeholder = "Leave a comment!";
	input.name = "description";
	input.className = "input-border commentinput";
	inputdiv.appendChild(input);

	//the little send button
	let send = document.createElement("i");
	send.className = "far fa-paper-plane marg-left i-click";
	send.addEventListener("click", () => {
		let val = $(".commentinput").value;
		let id = imgobj.id;
		if(val !== "")
			comment(val, id, updateImagesFunction);
		else
			alert("Please write a comment.");
	});
	inputdiv.appendChild(send);

	//again, appending all these created nodes onto the DOM
	commentsection.appendChild(inputdiv);

	//creating the logic for the comments
	let commentsect = document.createElement("div");
	commentsect.className = "commentsection";

	imgobj.comments.forEach(comment => {
		let c = renderComment(comment);
		commentsect.prepend(c);
	});
	commentsection.appendChild(commentsect);
	inner.appendChild(commentsection);
	modal.appendChild(exit);
	modal.appendChild(inner);
	document.body.appendChild(modal);

	//finally revealing the modal to the user while preventing scroll of the background
	modal.style.display = "block";
	document.body.style.overflow = "hidden";

};

const addModalListener = (el, imgobj, updateImagesFunction) => {
		if(!el.getAttribute("data-clickable"))
			el.addEventListener("click", (e) => {
				e.currentTarget.setAttribute("data-clickable", "true");
				createModal(imgobj, updateImagesFunction);
			});
};

const comment = (desc, id, updateImagesFunction) => {
	let form = new FormData();
	form.append("description", desc);
	form.append("id", id);

	fetch("/postComment", {method: "POST", body: form})
		.then(r => r.json())
		.then(data => {
			$(".commentsection").prepend(renderComment(data));
			updateImagesFunction();
		});

};

const renderComment = (comment) => {
	let c = document.createElement("div");
	c.className = "comment";
	let u = document.createElement("span");
	u.innerHTML = comment.username + " wrote:";
	u.className = "modalusername i-right";
	let span = document.createElement("p");
	span.innerHTML = comment.description;
	let cd = document.createElement("p");
	cd.className = "modaldate";
	cd.innerHTML = new Date(comment.date).toLocaleDateString();
	c.appendChild(u);
	c.appendChild(span);
	c.appendChild(cd);
	return c;
};