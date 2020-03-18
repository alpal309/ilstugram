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