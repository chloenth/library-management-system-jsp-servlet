<%@page import="common.ResultCode"%>
<%@page import="common.StringCommon"%>
<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
	<%
		String errorCode = request.getParameter("error");
		String message = null;
		
		if(errorCode != null && StringCommon.isInteger(errorCode)) {
			message = ResultCode.getMessageByCode(Integer.valueOf(errorCode));
		}
	%>


	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<h2 class="text-center my-4">Welcome Back!</h2>
				
				<% if(message != null) { %>
					<div class="alert alert-danger mt-4 mb-3" role="alert">
					  <%= message %>
					</div>
				<% } %>

				<form
					action="CheckLoginServlet"
					method="post">
					<div class="mb-3">
						<label
							for="username"
							class="form-label">UserName</label>
						<input
							type="text"
							class="form-control"
							id="username"
							name="username"
							required />
					</div>

					<div class="mb-3">
						<label
							for="password"
							class="form-label">Password</label>
						<input
							type="password"
							class="form-control"
							id="password"
							name="password"
							required />
					</div>

					<button
						type="submit"
						class="btn btn-primary w-100">Login</button>
				</form>

				<p class="text-center mt-3">
					Don't have an account? <a href="registerAdmin.jsp">Register here</a>
				</p>
			</div>
		</div>
	</div>


	<!-- Bootstrap JS and dependencies -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>