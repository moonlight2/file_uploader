<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:directive.include file="../jspf/header_doc.jspf" />
        <script type="text/javascript" src="<c:url value="/js/upload.js" ><c:param name="rand" value="" /></c:url><%=rand%>"></script>


    <div class="container top-width">
        <div id="fi-form"></div>
        <div id="prog-bar"></div>
    </div>
</body>
</html>
