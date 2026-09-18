/**
 * @param {*} obj
 * @param {*} classFunction
 * @return {boolean}
 */
var checkIfInstanceOf = function(obj, classFunction) {
    if(obj === null || obj === undefined || classFunction === null || classFunction === undefined ){
        return false;
    }
    if(typeof classFunction !== "function"){
        return false;
    }
    let prototype = Object.getPrototypeOf(Object(obj));
    const targetPrototype = classFunction.prototype;
    while(prototype!==null){
        if(prototype === targetPrototype){
            return true;
        }
        prototype = Object.getPrototypeOf(prototype);
    }
    return false;
};

/**
 * checkIfInstanceOf(new Date(), Date); // true
 */