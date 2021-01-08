module.exports = {
    transpileDependencies: ["vuetify"],
    publicPath: "/",
    devServer: {
        port: 8081
    },
    pwa: {
        name: "kfsNotes",
        appleMobileWebAppCapable: 'yes',
        appleMobileWebAppStatusBarStyle: 'black'
    }
}