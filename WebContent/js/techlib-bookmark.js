/*
<script type="text/javascript">
 */
	$(function(){
		$.ajax({
		     url:'bookmark.xml',
		     type: 'GET',
		     dataType: 'xml',//这里可以不写，但千万别写text或者html!!!
		     timeout: 1000,
		     error: function(xml){
		         //alert('Error loading XML document'+xml);
		    	 $('<tr><td colspan=4 >'+'&nbsp;&nbsp;&nbsp;&nbsp;请使用火狐浏览器打开，'
		    			 +'或<a href="bookmark.xml" targe="_blank">直接访问&nbsp;bookmark.xml</a>'+
	            		 +'</td></tr>')
	                 .appendTo('#mytable');
		     },
		     success: function(xml){
		         $(xml).find("bookmark").each(function(i){
		             var id_value=$(this).attr("id"); //取文本
		             var url = $(this).children("url").text(); 

		             var title = $(this).attr("title");
		             var resourceType = $(this).children("resource-type").text();
		             var technicalType = $(this).attr("technical-type");
		             var tags = $(this).children("tags").text().trim();
		             var titleAhref = '<a href="'+ url +'" title="技术标签：'+tags+'" target="_blank">'+title+'</a>';
		             var provider = $(this).children("provider").text();
		             
		             //最后么输出了，这个是cssrain的写法，貌似比macnie更JQ一点
		             $('<tr><td class="row">'+id_value+'</td><td class="row">'
		            		 +technicalType+'</td><td class="row">'+ titleAhref
		            		 +'&nbsp;('+resourceType+')'
		            		 +'</td><td>'+provider
		            		 +'</td></tr>')
		                 .appendTo('#mytable');
		         });
		     }
		 });

		
	});
/*
</script>
*/