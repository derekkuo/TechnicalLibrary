/*
<script type="text/javascript">
 */
	$(function(){

		$.ajax({
		     url:'bookmark.xml',
		     type: 'GET',
		     dataType:($.browser.msie) ? "text" : "xml",
		     //dataType: 'XML',//这里可以不写，但千万别写text或者html!!!
		     timeout: 1000,
		     error: function(xml){
		         //alert('Error loading XML document'+xml);
		    	 $('<tr><td colspan=4 >'+'&nbsp;&nbsp;&nbsp;&nbsp;请使用火狐浏览器打开，'
		    			 +'或<a href="bookmark.xml" targe="_blank">直接访问&nbsp;bookmark.xml</a>'+
	            		 +'</td></tr>')
	                 .appendTo('#mytable');
		     },
		     success: function(data){
		    	 var xml;
				if( typeof data == "string" ){
				         xml = new ActiveXObject("Microsoft.XMLDOM");
				         xml.async = false;
				         xml.loadXML(data);
				} else {
				         xml = data; 
				}
		         $(xml).find("bookmark").each(function(i){
		             var id_value=$(this).children("id").text(); //取文本
		             
		             var url = $(this).children("url").text(); 

		             var title = $(this).children("title").text();
		             var resourceType = $(this).children("resource-type").text();
		             var technicalType = $(this).children("technical-type").text();
		             var tags = $(this).children("tags").text();
		             var summary = $(this).children("summary").text();
		             if(summary!='')
		            	 var titleAhref = '<a href="'+ url +'" title="技术标签：'+tags+'&nbsp;摘要：'+summary+'" target="_blank">'+title+'</a>';
		             else
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