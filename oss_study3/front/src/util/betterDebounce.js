function betterDebounce(fn, time) {
  var timer
  var last = 0
  return function () {
    var now = +new Date()

    if (now - last < time) {
      clearTimeout(timer)

      timer = setTimeout(function () {
        last = now
        fn.apply(this, arguments)
      }, time)
    } else {
      clearTimeout(timer)
      last = now
      fn.apply(this, arguments)
    }
  }
}
export default betterDebounce