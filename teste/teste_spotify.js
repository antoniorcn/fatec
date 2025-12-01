const request = require("request")
var client_id = '9b45f364bd204eb396723099f754d8d8';
var client_secret = 'c1086983408a493db3410bf1d3154d91';
var code = undefined;

// var authOptions = {
//   url: 'https://accounts.spotify.com/api/token',
//   headers: {
//     'Authorization': 'Basic ' + (new Buffer.from(client_id + ':' + client_secret).toString('base64'))
//   },
//   form: {
//     grant_type: 'client_credentials'
//   },
//   json: true
// };

// request.post(authOptions, function(error, response, body) {
//   if (!error && response.statusCode === 200) {
//     var token = body.access_token;
//     console.log("Token: ", token);
//   }
// });

// const clientId = "9b45f364bd204eb396723099f754d8d8"; // Replace with your client id
// const code = undefined;

async function getToken(client_id, client_secret) {
  const autorizationString = 'Basic ' + (new Buffer.from(client_id + ':' + client_secret).toString('base64'));
  console.log("Autorization String: " + autorizationString);

  const result = await fetch("https://accounts.spotify.com/api/token?grant_type=client_credentials", {
    method: "POST",
    headers: { 
      "Authorization": autorizationString,
      "Content-Type": "application/x-www-form-urlencoded"
    }});

  const { access_token } = await result.json();
  return access_token;
}

async function redirectToAuthCodeFlow(clientId) {
  const verifier = await generateCodeVerifier(128);
  const challenge = await generateCodeChallenge(verifier);

  localStorage.setItem("verifier", verifier);

  const params = new URLSearchParams();
  params.append("client_id", clientId);
  params.append("response_type", "code");
  params.append("redirect_uri", "http://localhost:5173/callback");
  params.append("scope", "user-read-private user-read-email");
  params.append("code_challenge_method", "S256");
  params.append("code_challenge", challenge);

  document.location = `https://accounts.spotify.com/authorize?${params.toString()}`;
}

async function getAccessToken(clientId, code) {
  const verifier = localStorage.getItem("verifier");

  const params = new URLSearchParams();
  params.append("client_id", clientId);
  params.append("grant_type", "authorization_code");
  params.append("code", code);
  params.append("redirect_uri", "http://localhost:5173/callback");
  params.append("code_verifier", verifier);

  const result = await fetch("https://accounts.spotify.com/api/token", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: params
  });

  const { access_token } = await result.json();
  return access_token;
}

async function fetchProfile(token){
  const result = await fetch("https://api.spotify.com/v1/me", {
      method: "GET", headers: { Authorization: `Bearer ${token}` }
  });

  return await result.json();
}

function populateUI(profile) {
    console.log("Dados do usuario: ");
    console.log(profile);
    console.log("Profile ID: ",  profile.id)
    console.log("Profile Email: ",  profile.email);
    console.log("Profile URI: ",  profile.uri);
    console.log("Profile External URL: ",  profile.external_urls.spotify);
}


function generateCodeVerifier(length) {
  let text = '';
  let possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

  for (let i = 0; i < length; i++) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
  }
  return text;
}

async function generateCodeChallenge(codeVerifier) {
  const data = new TextEncoder().encode(codeVerifier);
  const digest = await window.crypto.subtle.digest('SHA-256', data);
  return btoa(String.fromCharCode.apply(null, [...new Uint8Array(digest)]))
      .replace(/\+/g, '-')
      .replace(/\//g, '_')
      .replace(/=+$/, '');
}

async function callAll() { 
  console.log("Pegando o acess token")
  const accessToken = await getToken(client_id, client_secret);
  const profile = await fetchProfile(accessToken);
  populateUI(profile);
}


console.log("Iniciando o programa")
callAll()
.then(()=>{
  console.log("callAll executado com sucesso")
})
.catch((e)=>{
  console.log("Erro ao executar o callAll");
  console.error(e);
})

