define(['axios', 'qs'], function(Axios, qs){
  const web = Axios
  web.fPost = (url, param, config = null) => {
    return Axios.post(url, qs.stringify(param, {indices: false}), config)
  }

  web.jPost = (url, param, config = {headers: {'Content-Type': 'application/json'}}) => {
    return Axios.post(url, JSON.stringify(param), config)
  }
  return web
})