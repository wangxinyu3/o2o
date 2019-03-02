$(function () {
  var shopId = getQueryString('shopId');
  var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
  $.getJSON(shopInfoUrl, function (data) {
      console.log(data.redirect);
      console.log(data.shopId);
      if (data.redirect){
          window.location.href = data.url;
      }else {
          if (data.shopId != undefined && data.shopId != null){
              shopId = data.shopId;
          }
          $('#shopinfo').attr('href', "/o2o/shopadmin/shopoperation?shopId=" + shopId);
      }
  });
});