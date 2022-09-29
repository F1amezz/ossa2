"use strict";

import axios from "axios";
let config = {
	timeout: 60 * 1000 // Timeout
};
const _axios = axios.create(config);
export default _axios;
