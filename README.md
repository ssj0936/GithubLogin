# Loading Signin info via Github oAuth
<p>
<img src="https://user-images.githubusercontent.com/3841546/134490872-6f1b1393-8827-436b-9369-bb4877b84a01.png" width="270" height="612">
<img src="https://user-images.githubusercontent.com/3841546/134490909-7f2c0de3-68e9-492e-a6ee-34109e6f15ac.png" width="270" height="612">
</p>


## Intro

This is a sign in UI interface supporting github oAuth that implements MVVM architecture using Hilt, Coroutine and Retrofit2.

According [oAuth document of github](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps), there are 3 steps for
granting authorization and fetching data from github

1. Request a user's GitHub identity
    * In this app it will launch web browser showing login github page. After login, github will asking for authorizing permission of some scope of data(e.g. User info here) to this app.
     
2. Users are redirected back to your site by GitHub 
    * After user accept the request, Github redirects back with a temporary **code**.
    * App requests oAuth **token** with this code
3. Use the access token to access the API
    * After getting token successfully, app is granted for access API with legal token.


## Project Structure
The project has following packages:
1. **di**: Module of Hilt
2. **localData**: local data source for storing token(sharePreference)
3. **model**: POJOs (model of webapi response)
4. **network**: web data source for asking token and fetching user info (Retrofit2)
5. **repository**: Assembled data source
6. **utils**: Utility classes

## Detail

0. Before all of this, [Creating an OAuth App](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app) is required. After creating you will get **Client Id** and **Client secret**. **Client Id** is required parameter for requesting identity code & token, and **Client secret** is required parameter for requesting token.

1. Another important part on oAuth app creating page is the column called **Authorization callback URL**. You need to fill in **app-link** of the activity that you want to receive requesting result. (you can take a look of Manifest.xml)

2. flow chart of the oAuth

![](https://i.imgur.com/HrUMFRO.png)

3. For the part of getting identity code, it seems that the only way for allowing redirect is a simple **startActivity** with request uri then getting code on **onNewIntent()**.

## Reference
[Token-Based Authentication with Retrofit | Android OAuth 2.0](https://medium.com/android-news/token-authorization-with-retrofit-android-oauth-2-0-747995c79720)

[GitHub ????????????????????? OAuth 2.0](https://medium.com/wenchin-rolls-around/github-%E5%B8%B3%E8%99%9F%E7%99%BB%E5%85%A5-%E9%80%8F%E9%81%8E-oauth-2-0-be3a0d986db8)

[GitHub - Building OAuth Apps](https://docs.github.com/en/developers/apps/building-oauth-apps)

[??????App Link???Deep Link??????](https://medium.com/%E5%B7%A5%E7%A8%8B%E5%B8%AB%E6%B1%82%E7%94%9F%E6%8C%87%E5%8D%97-sofware-engineer-survival-guide/%E6%B7%BA%E8%AB%87app-link%E8%88%87deep-link%E5%AF%A6%E4%BD%9C-995734a11889)


