<!DOCTYPE html>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.discovery.digital.service.facebook.leads.FacebookLeadsServlet" %>--%>
<html>
    <head>
        <title>Facebook Lead Subscription Platform</title>
    </head>
    <body>
        <h2>Facebook Lead Subscription Platform</h2>
        <script>
            window.fbAsyncInit = function () {
                FB.init({
                    appId: '1564580960228927',
                    autoLogAppEvents: true,
                    xfbml: true,
                    version: 'v2.10'
                });
                FB.AppEvents.logPageView();
            };

            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {
                    return;
                }
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));

            function subscribeApp(page_id, page_access_token) {
                console.log('Subscribed Page to FB Leads Live Update ' + page_id);
                FB.api('/' + page_id + '/subscribed_apps',
                        'post',
                        {access_token: page_access_token},
                        function (response)
                        {
                            console.log('Successfully subscribed page', response);
                        });

            }
            function checkLoginState() {
                FB.getLoginStatus(function (response) {
                    console.log('statusChangeCallback');
                    console.log(response);
                    console.log('successfully logged in', response);
                });

                FB.login(function (response) {
                    if (response.status == 'connected') {
                        // Logged into your app and Facebook.
                        FB.api('me/accounts', function (response) {
                            console.log('successfully retrieved pages', response);

                            var pages = response.data;
                            var ul = document.getElementById('list');
                            for (i = 0, len = pages.length; i < len; i++)
                            {
                                var page = pages[i];
                                var li = document.createElement('li');
                                var a = document.createElement('a');
                                a.href = "#";
                                a.onclick = subscribeApp.bind(this, page.id, page.access_token);
                                li.appendChild(a);
                                a.innerHTML = page.name;
                                ul.appendChild(li);
                            }
                        });

                    } else if (response.status == 'not_authorized') {
                        // The person is logged into Facebook, but not your app.
                    } else {
                        // The person is not logged into Facebook, so we're not sure if
                        // they are logged into this app or not.
                    }
                }, {scope: 'public_profile,manage_pages'});

            }

        </script>


    <fb:login-button scope="public_profile,manage_pages" onlogin="checkLoginState();">
    </fb:login-button>

    <ul id="list"></ul>

</body>
</html>