const SERVER_URL = 'http://localhost:8080'
module.exports = {
    devServer: {
        port: 6080,
        proxy: {
            '/api': {
                target: SERVER_URL,
                changeOrigin: true,
                secure: false,
                pathRewrite: {
                    '^/api': '/api'
                }
            }
        }
    }
}