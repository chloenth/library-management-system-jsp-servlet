<%@page import="common.StringCommon"%>
<%@page import="common.ResultCode"%>
<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>

	<%
		String errorCode = request.getParameter("error");
		String message = null;
	
		if (errorCode != null && StringCommon.isInteger(errorCode)) {
			message = ResultCode.getMessageByCode(Integer.valueOf(errorCode));
		}
	%>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<h2 class="text-center my-3">Create Your Account</h2>

				<%
				if (message != null) {
				%>
				<div
					class="alert alert-danger mt-4 mb-3"
					role="alert">
					<%=message%>
				</div>
				<%
				}
				%>

				<form
					action="RegisterServlet"
					method="post"
					class="mt-4">
					<div class="mb-3">
						<label
							for="username"
							class="form-label">Username</label>
						<input
							type="text"
							class="form-control"
							id="username"
							name="username"
							required />
					</div>

					<div class="mb-3">
						<label
							for="fullname"
							class="form-label">Full Name</label>
						<input
							type="text"
							class="form-control"
							id="fullname"
							name="fullname"
							required />
					</div>

					<div class="mb-3">
						<label
							for="email"
							class="form-label">Email Address</label>
						<input
							type="email"
							class="form-control"
							id="email"
							name="email"
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

					<div class="mb-3">
						<label
							for="confirmPassword"
							class="form-label">Confirm Password</label>
						<input
							type="password"
							class="form-control"
							id="confirmPassword"
							name="confirmPassword"
							required />
					</div>

					<button
						type="submit"
						class="btn btn-primary w-100">Register</button>
				</form>

				<p class="text-center mt-3">
					Already have an account? <a href="login.jsp">Login here</a>
				</p>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS and dependencies -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>