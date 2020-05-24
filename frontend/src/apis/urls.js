const urlBase = "http://localhost:8080/";
const apiBase = `${urlBase}api/`
const githubUrlBase = "https://github.com";


export const urlProject = `${apiBase}project`;
export const urlAnalysisProject = projectKey => `${apiBase}analysis?projectKey=${projectKey}`;
export const urlStartAnalysis = projectKey => `${apiBase}project/analysis?key=${projectKey}`;
export const urlCurrentAnalysisStatus = id => `${apiBase}analysis/status?id=${id}`;
export const urlAccessToken = `${apiBase}github/accesstoken/`
export const urlCurrentInstallation = id => `${apiBase}github/installation/${id}`;
export const urlAddBearerTokenToProject = `${apiBase}github/bearertoken`;
export const urlGenerateReport = projectKey => `${apiBase}project/report?key=${projectKey}`;


export const githubUrlAuthorize = `${githubUrlBase}/login/oauth/authorize`;
export const githubUrlAccessToken = `${githubUrlBase}/login/oauth/access_token`;
