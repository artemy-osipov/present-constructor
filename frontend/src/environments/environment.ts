export const environment = {
  production: false,
  appId: 'frontend_id',
  apiHost: 'localhost:8088',
  enableSecurity: false,

  get apiUrl() {
    return 'http://' + this.apiHost + '/';
  }
};
