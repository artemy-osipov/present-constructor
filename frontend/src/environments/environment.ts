export const environment = {
  production: false,
  appId: 'frontend_id',
  apiHost: '192.168.1.105:8088',
  enableSecurity: false,

  get apiUrl() {
    return 'http://' + this.apiHost + '/';
  }
};
