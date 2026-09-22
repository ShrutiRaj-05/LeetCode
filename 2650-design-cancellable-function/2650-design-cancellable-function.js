/**
 * @param {Generator} generator
 * @return {[Function, Promise]}
 */
function cancellable(generator) {
    let cancelled = false;

    let cancel;

    const promise = new Promise((resolve, reject) => {

        cancel = () => {
            if (!cancelled) {
                cancelled = true;

                try {
                    const result = generator.throw("Cancelled");

                    if (result.done) {
                        resolve(result.value);
                    } else {
                        reject("Cancelled");
                    }
                } catch (error) {
                    reject(error);
                }
            }
        };

        function run(result) {
            if (result.done) {
                resolve(result.value);
                return;
            }

            Promise.resolve(result.value)
                .then(value => {
                    if (cancelled) {
                        return;
                    }

                    try {
                        run(generator.next(value));
                    } catch (error) {
                        reject(error);
                    }
                })
                .catch(error => {
                    if (cancelled) {
                        return;
                    }

                    try {
                        run(generator.throw(error));
                    } catch (error) {
                        reject(error);
                    }
                });
        }

        try {
            run(generator.next());
        } catch (error) {
            reject(error);
        }
    });

    return [cancel, promise];
}