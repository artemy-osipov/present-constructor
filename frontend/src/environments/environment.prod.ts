export const environment = {
  production: true,
  appId: 'frontend_id',
  apiHost: 'localhost:8080',

  get apiUrl() {
    return 'http://' + this.apiHost;
  }
};
