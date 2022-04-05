function updataDataByIndex(index, id, path){
    document.getElementById(id).action = path+"/"+index;
    document.getElementById(id).submit;
    console.log(path+"/"+index);
}

function deleteDataByIndex(index, id, path){
    var method = document.getElementsByName("_method");
    method[0].value="DELETE";
    updataDataByIndex(index, id, path);
}

function resetPage(index, id, path){
    document.getElementById(id).action = path+"/resetPage";
    var method = document.getElementsByName("_method");
    method[0].value="POST";
    location.href= path+"/resetPage";
}



