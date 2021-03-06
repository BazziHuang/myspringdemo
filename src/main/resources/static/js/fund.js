/*
dependencies:
jquery-3.6.0.min.js
util.js
jquery.validate.min.js
messages_zh_TW.min.js
*/

$(function () {
    //驗證註冊
    $('#fundform').validate({
        onsubmit: false,
        onkeyup: false,
        rules: {
            name: {
                required: true,
                rangelength: [2, 50]
            }
        },
        messages: {
            name: {
                required: "請輸入基金名稱",
                rangelength: "基金名稱長度必須介於{0}~{1}之間"
            }
        }
    });
    //Fund List 的資料列表
    table_list();
    setPageLegend();
    //註冊相關事件
    $('#fundtable').on('click', 'tr', function () {  //tr指的是table的row, 點按就會呼喚getItem()
        getItem(this);
    });
    $('#add').on('click', function () {
        addOrUpdate('POST');
    });
    $('#update').on('click', function () {
        addOrUpdate('PUT');
    });
    $('#delete').on('click', function () {
        deleteItem();
    });
    $('#reset').on('click', function () {
        btnAttr(0);
    });

});

function queryPage(pageNumber) {
    var path = "/stock/fund/rawdata/";
    if (pageNumber > 0) {
        path += "page/" + pageNumber;
    }
    $.get(path, function (data, status) {
        //console.log(data);
        //console.log(status);
        $('#fundtable tbody > tr').remove();
        $.each(data, function (i, item) {
            var html = '<tr><td><a href="#" onclick="getData({0})">{0}</a</td><td>{1}</td><td width="12%">{2}</td><td width="65%">{3}</td></tr>'; //共四個欄位 id, name, createtime, fundstocls
            if (item.fundstocks != null) {
                $('#fundtable').append(String.format(
                    html, item.id, item.name, item.createtime, Object.values(item.fundstocks)
                        .map(item => ' ' + item.name + '(' + item.symbol + ')')
                ));
            } else {
                $('#fundtable').append(String.format(
                    html, item.id, item.name, item.createtime, '')
                );
            }
        });
    });
}

function setPageLegend() {
    var path = "/stock/fund/rawdata/totalPageCount";
    $.get(path, function (data, status) {
        //console.log('legend: ' + data);
        //console.log('legend: ' + status);
        $('#fundtablelegend legend').remove();
        var html = '<a class="text-primary" href="#" onclick="queryPage({0});">{1}</a>&nbsp;|&nbsp;';
        for (var i = 1; i <= data; i++) {
            //console.log(i);
            $('#fundtablelegend').append(String.format(
                html, i, i
            ));
        }
    });
}

function getData(fid) {
    var path = '/stock/fund/rawdata/' + parseInt(fid);
    var func = function (fund, status) {  //fund  =>  從path拿到的data
        //console.log(fund);
        //將data注入到form
        $('#fundform').find('#id').val(fund.id);
        $('#fundform').find('#name').val(fund.name);
        btnAttr(1);
        //console.log(fund.fundstocks.length);
        /*
        if(fund.fundstocks.length > 0){
            $('#fundform').find('#delete').attr('disabled',true);
        }
        */
    }
    $.get(path, func);   // *****執行
}

function logout(){
    console.log('logout');
    $.get('/stock/logout',function(){
        alert('已成功登出，將回到登入頁面');
        window.location.href="/login/";
    });
    /*
    $(function(){
        alert('已成功登出，將回到登入頁面');
        sessionStorage.clear();
        window.location.href="/login/";
    });
    */
}

function getItem(elem) {
    var fid = $(elem).find('td').eq(0).text().trim(); //呼喚<td>{0}</td>的內容  此處為fund_id
    console.log(fid);
    var path = '/stock/fund/rawdata/' + fid;
    var func = function (fund, status) {  //fund  =>  從path拿到的data
        //console.log(fund);
        //將data注入到form
        $('#fundform').find('#id').val(fund.id);
        $('#fundform').find('#name').val(fund.name);
        btnAttr(1);
        //console.log(fund.fundstocks.length);
        /*
        if(fund.fundstocks.length > 0){
            $('#fundform').find('#delete').attr('disabled',true);
        }
        */
    }
    $.get(path, func);   // *****執行
}

function addOrUpdate(method) {
    //console.log($('#fundform').valid());
    if (!$('#fundform').valid()) {
        return;
    }
    var jsonObject = $('#fundform').serializeObject(); //這邊會抓input標籤name作為物件名
    var jsonString = JSON.stringify(jsonObject);  //物件名要和Fund.java封裝名稱相同
    //console.log("jsonString: " + jsonString);
    $.ajax(
        {
            url: "/stock/fund/rawdata/",
            type: method,
            contentType: 'application/json;charset=utf-8',
            data: jsonString,
            success: function (respData) {
                //console.log("respData: " + respData);
                table_list();
                btnAttr(0);
                $('#fundform').trigger('reset');
            },
            error: function (http, textStatus, errorThrown) {
                var errorInfoText = JSON.stringify(http);
                //console.log(errorInfoText);
                //console.log(errorInfoText.includes('DataAlreadyExistsException'));
                if (errorInfoText.includes('DataAlreadyExistsException')) {
                    alert('該筆資料名稱已經存在，資料無法新增或修改');
                }
            }

        }
    );
}

function deleteItem() {
    var fid = $('#fundform').find('#id').val();
    $.ajax({
        url: '/stock/fund/rawdata/' + fid,
        type: 'DELETE',
        contentType: 'application/json;charset=utf-8',
        success: function (respData) {
            //console.log("respData: " + respData);
            table_list();
            btnAttr(0);
            $('#fundform').trigger('reset');
        },
        error: function (http, textStatus, errorThrown) {
            //console.log('http: ' + http);
            //console.log('textStatus: ' + textStatus);
            //console.log('errorThrown: ' + errorThrown);
            var errorInfoText = JSON.stringify(http);
            //console.log(errorInfoText.includes('referenced'));
            if (errorInfoText.includes('referenced')) {
                alert('該筆資料無法刪除，原因：因為基金下有成分股');
            } else {
                alert('該筆資料無法刪除，原因：' + textStatus);
            }
        }
    });
}

function table_list() {
    queryPage(1);
}

function btnAttr(status) {
    $('#fundform').find('#add').attr('disabled', status != 0);
    $('#fundform').find('#update').attr('disabled', status == 0);
    $('#fundform').find('#delete').attr('disabled', status == 0);
}