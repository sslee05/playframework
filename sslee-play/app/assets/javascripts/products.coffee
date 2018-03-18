jQuery ($) ->
	$table = $('.container table') # html table 요소 
	productListUrl = $table.data('list') # controllers.jsonchapter.routes.ProductController.list url
	
	$.get productListUrl, (products) ->  # 2번째 인자 (products) -> .. 함수는 callback 함수 
		$.each products, (index,eanCode) ->  # Ajax GET 요청 
			row = $('<tr/>').append $('<td/>').text(eanCode)
			$table.append row # 각 상품에 대한 table row 추가 