const urlBase = "http://localhost:8080/";
const apiBase = `${urlBase}api/`
const githubUrlBase = "https://github.com";


export const urlProject = `${apiBase}project`;
export const urlAccessToken = `${apiBase}github/accesstoken/`

export const githubUrlAuthorize = `${githubUrlBase}/login/oauth/authorize`;
export const githubUrlAccessToken = `${githubUrlBase}/login/oauth/access_token`;
