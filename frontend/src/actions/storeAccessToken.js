import { createAction } from "redux-actions";
import { githubUrlAccessToken } from "../apis/urls";
// import { apiPost } from "../apis";
import { STORE_ACCESS_TOKEN } from "../constants/github";
import { createOAuthAppAuth } from "@octokit/auth-oauth-app";




const generateUrl = code => 
// `${githubUrlAccessToken}?client_id=Iv1.9ad3617300b9f691&client_secret=d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700&code=${code}&redirect_uri=http://localhost:3000`;
  `${githubUrlAccessToken}?client_id=Iv1.9ad3617300b9f691&client_secret=d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700&code=${code}`;


const apiGet = url => () => 
  fetch(
    url,
    { 
      mode: 'no-cors',
      headers: new Headers({ "Accept": "application/vnd.github.v3+json" })
    }
  )
  .then(r => {
    setTimeout(null, 3000);
    debugger;
    return r;
  })
  .then(v => {
    debugger;
    return v.text();
  });

const apiPost = (url, obj) => () =>
  fetch(
    `${url}`, 
    {
      method: "POST",
      body: JSON.stringify(obj),
      headers: new Headers({ "accept": "application/json" }),
      mode: 'no-cors'
    }
  )
  .then(v => v.json())
  .then(r => {
    if (r.error) {
      return Promise.reject(r.validation);
    }
    return r;
  });

const generateOpts = code => ({
  type: "oauth-app",
  clientId: "Iv1.9ad3617300b9f691",
  clientSecret: "d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700",
  code,
  headers: {},
  query: {
    clientId: "Iv1.9ad3617300b9f691",
    clientSecret: "d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700",
    code
  }
});

// const auth = createOAuthAppAuth({
//   clientId: "1234567890abcdef1234",
//   clientSecret: "1234567890abcdef1234567890abcdef12345678",
//   code: "random123" // code from OAuth web flow, see https://git.io/fhd1D
// });
 
// const appAuthentication = await auth({
//   type: "oauth-app",
//   url: "http://localhost:3030"
// });

// const tokenAuthentication = /* await */ auth({ type: "token" });

export const requestForAccessToken = async code => {
  const auth = createOAuthAppAuth({
    clientId: "Iv1.9ad3617300b9f691",
    clientSecret: "d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700",
    code
  });
  const tokenAuthentication = await auth({ type: "token" });

  return tokenAuthentication;
};

const apiPost2 = async (url, obj) => () => {
  const response = fetch(
    `${url}`, 
    {
      method: "POST",
      body: JSON.stringify(obj),
      headers: new Headers({ "accept": "application/json" }),
      mode: 'no-cors'
    }
  );
  const json = response.json();
  if (json.error) {
    return Promise.reject(json.validation);
  }
  return json;
};


export const storeAccessToken = createAction(STORE_ACCESS_TOKEN, 
  // (body, code) => apiPost(githubUrlAccessToken, { ...body, code  })()
  // code => apiPost(generateUrl(code), {})()
  token => token
);