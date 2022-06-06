import {useDispatch, useSelector} from "react-redux";

export default function ErrorList({_useSelector=useSelector, _useDispatch=useDispatch}) {
    const dispatch = _useDispatch()
    const errorList =_useSelector(state => state.user?.errorList)

    return errorList.length === 0
            ? <></>
            : <div>
                <ul>
                    {errorList?.map((error, index) => <li key={index}>
                        {error}
                    </li>)
                    }
                </ul>
                <button>Clear Errors</button>
            </div>
}