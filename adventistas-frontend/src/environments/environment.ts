export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  mediaUrl: 'http://localhost:8080/api/media',
  defaultLanguage: 'es',
  tokenKey: 'auth_token',
  userKey: 'current_user',
  pagination: {
    pageSize: 10,
    pageSizeOptions: [5, 10, 20, 50]
  }
};