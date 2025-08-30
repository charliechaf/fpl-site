const path = require('path');

const rootDir = path.resolve(__dirname, '../../../..');

config.devServer = {
  ...config.devServer,
  static: [
    {
      directory: path.resolve(rootDir, 'src/main/resources'),
      publicPath: '/',
    },
    {
      directory: path.resolve(rootDir, 'build/processedResources/js/main'),
      publicPath: '/',
    }
  ],
  historyApiFallback: {
    index: '/index.html'
  },
  port: 3000,
  open: false
};