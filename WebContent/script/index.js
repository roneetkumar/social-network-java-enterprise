document.querySelector(".search-bar input").onfocus = () =>{
	document.querySelector(".overlay").style.display = "initial";
}

document.querySelector(".overlay").onclick = () =>{
	document.querySelector(".overlay").style.display = "none";
}

let users = document.querySelectorAll(".users .user");


let search = document.querySelector(".search-bar input");

search.onkeyup = () =>{	
	users.forEach(ele=>{
			let text = search.value.toLowerCase();
			let from = ele.querySelector("h1").innerText.toLowerCase();
			if(!from.includes(text)){
				ele.style.display = "none"			
			}else if(text === ""){
				ele.style.display = "none";
			}else{
				ele.style.display = "flex"		
			}
		})
}